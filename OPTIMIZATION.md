# 代码优化总结

## 优化概述

| 类型 | 优化项 | 文件 |
|------|--------|------|
| 代码质量 | 移除死代码 | OrderServiceImpl.java |
| 性能优化 | Stream API 替代循环求和 | OrderItemServiceImpl.java |
| 代码质量 | 重构重复逻辑为辅助方法 | ForeController.java |
| 代码质量 | 异常处理改为 Slf4j 日志 | ImageUtil.java |
| 代码质量 | 使用现代 ImageIO API | ImageUtil.java |
| 金额精度 | float → BigDecimal | Product.java, Order.java, OrderService |
| 基础设施 | Druid → HikariCP | pom.xml |
| 日志框架 | Log4j → Logback | pom.xml |
| 开发工具 | 添加 DevTools 和 Actuator | pom.xml |
| 性能优化 | N+1 查询问题修复 | ProductService, OrderItemServiceImpl |
| 并发优化 | 乐观锁 + 原子库存扣减 | Product.java, ProductMapper.xml |

---

## 1. OrderServiceImpl.java - 移除死代码

**问题**: `if(false)` 死代码块

**优化前**:
```java
if (false) {
    throw new RuntimeException();
}
```

**优化后**: 直接移除该代码块

---

## 2. OrderItemServiceImpl.java - 性能优化

**问题**: `getSaleCount()` 方法使用循环求和

**优化前**:
```java
public int getSaleCount(int pid) {
    List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
    int result = 0;
    for (OrderItem orderItem : orderItems) {
        result += orderItem.getNumber();
    }
    return result;
}
```

**优化后**:
```java
public int getSaleCount(int pid) {
    List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
    return orderItems.stream().mapToInt(OrderItem::getNumber).sum();
}
```

**效果**:
- 代码更简洁
- 利用 Stream API 的优化
- 更容易并行化（未来可扩展）

---

## 3. ForeController.java - 重构重复逻辑

**问题**: `buyOne` 和 `addCart` 方法有完全相同的购物车项查找和更新逻辑

**优化前** (两个方法都有这段代码):
```java
List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
for (OrderItem orderItem : orderItems) {
    if (orderItem.getProduct().getId().intValue() == product.getId().intValue()) {
        orderItem.setNumber(orderItem.getNumber() + num);
        orderItemService.update(orderItem);
        found = true;
        break;
    }
}
if (!found) {
    OrderItem orderItem = new OrderItem();
    orderItem.setUid(user.getId());
    orderItem.setNumber(num);
    orderItem.setPid(pid);
    orderItemService.add(orderItem);
}
```

**优化后**:
提取公共方法 `addOrUpdateCartItem()`:

```java
/**
 * Helper method to add or update a cart item.
 */
private int addOrUpdateCartItem(int uid, int pid, int num) {
    List<OrderItem> orderItems = orderItemService.listByUser(uid);
    for (OrderItem orderItem : orderItems) {
        if (orderItem.getProduct().getId().intValue() == pid) {
            orderItem.setNumber(orderItem.getNumber() + num);
            orderItemService.update(orderItem);
            return orderItem.getId();
        }
    }
    OrderItem orderItem = new OrderItem();
    orderItem.setUid(uid);
    orderItem.setNumber(num);
    orderItem.setPid(pid);
    orderItemService.add(orderItem);
    return orderItem.getId();
}
```

**效果**:
- 消除代码重复 (DRY 原则)
- 更易于维护和修改
- 方法职责更单一

---

## 4. ImageUtil.java - 现代化重构

### 4.1 异常处理改进

**问题**: 使用 `e.printStackTrace()` 和遗留的 TODO 注释

**优化前**:
```java
} catch (InterruptedException e) {
    e.printStackTrace();
    return null;
}
```

**优化后**:
```java
} catch (IOException e) {
    log.error("Failed to convert image to JPEG: {}", f.getAbsolutePath(), e);
    return null;
}
```

**效果**:
- 使用 Slf4j 日志框架，符合 Spring Boot 标准
- 日志包含结构化信息
- 可配置日志级别和输出

### 4.2 使用现代 ImageIO API

**问题**: 使用已弃用的 `Toolkit.getDefaultToolkit().createImage()` 和 `PixelGrabber`

**优化前**:
```java
public static BufferedImage change2jpg(File f) {
    Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
    PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
    pg.grabPixels();
    // ... complex pixel manipulation
}
```

**优化后**:
```java
public static BufferedImage change2jpg(File f) {
    try (ImageInputStream iis = ImageIO.createImageInputStream(f)) {
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (!readers.hasNext()) {
            log.warn("No image reader found for file: {}", f.getAbsolutePath());
            return null;
        }
        ImageReader reader = readers.next();
        reader.setInput(iis);
        BufferedImage image = reader.read(0);
        reader.dispose();
        // ... simple RGB conversion if needed
    }
}
```

**效果**:
- 使用标准 ImageIO API，更稳定
- 自动资源管理 (try-with-resources)
- 支持更多图片格式

### 4.3 改进图片缩放算法

**优化前**:
```java
buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
```

**优化后**:
```java
Graphics2D g2d = buffImg.createGraphics();
g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
g2d.drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
g2d.dispose();
```

**效果**:
- 使用 Graphics2D 进行高质量缩放
- 显式释放资源
- 添加插值算法优化

---

## 验证

所有优化已通过编译验证:

```bash
mvn clean compile
# BUILD SUCCESS
```

---

## 5. 金额精度优化：float → BigDecimal

### 问题

使用 `float` 或 `double` 进行金额计算会导致精度丢失：

```java
// float 计算可能丢失精度
float result = 0.1f + 0.2f;  // 结果可能是 0.30000004
```

### 优化内容

**1. Product.java - 价格字段改为 BigDecimal**

```java
// 优化前
private Float originalPrice;
private Float promotePrice;

// 优化后
private BigDecimal originalPrice;
private BigDecimal promotePrice;
```

**2. Order.java - 订单总额改为 BigDecimal**

```java
// 优化前
private float total;

// 优化后
private BigDecimal total;
```

**3. MyBatis Mapper - JDBC 类型改为 DECIMAL**

```xml
<!-- 优化前 -->
<result column="originalPrice" jdbcType="REAL" property="originalPrice"/>

<!-- 优化后 -->
<result column="originalPrice" jdbcType="DECIMAL" property="originalPrice"/>
```

**4. 服务层 - 金额计算使用 BigDecimal API**

```java
// 优化前
float total = 0;
for (OrderItem orderItem : orderItems) {
    total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
}

// 优化后
BigDecimal total = BigDecimal.ZERO;
for (OrderItem orderItem : orderItems) {
    BigDecimal itemTotal = orderItem.getProduct().getPromotePrice()
            .multiply(BigDecimal.valueOf(orderItem.getNumber()));
    total = total.add(itemTotal);
}
```

**5. Comparator - 使用 compareTo 方法**

```java
// 优化前
return (int) (o1.getPromotePrice() - o2.getPromotePrice());

// 优化后
return o1.getPromotePrice().compareTo(o2.getPromotePrice());
```

### 效果

- 避免浮点运算精度问题
- 金额计算精确到分
- 符合金融系统设计规范

---

## 6. N+1 查询问题修复

### 问题

`OrderItemServiceImpl.setProduct()` 在循环中逐个调用 `productService.get()`，导致 N+1 查询问题：

```java
// 优化前：N+1 查询
private void setProduct(List<OrderItem> orderItems) {
    for (OrderItem orderItem : orderItems) {
        // 每个 OrderItem 都执行一次数据库查询！
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }
}
```

如果有 10 个 OrderItem，就会执行 10 次 SELECT 查询。

### 解决方案

**1. 添加批量查询方法到 ProductService**

```java
/**
 * 批量查询产品，避免 N+1 问题
 */
List<Product> listByIds(List<Integer> ids);
```

**2. 实现批量查询**

```java
@Override
public List<Product> listByIds(List<Integer> ids) {
    if (ids == null || ids.isEmpty()) {
        return new ArrayList<>();
    }
    ProductExample example = new ProductExample();
    example.createCriteria().andIdIn(ids);
    List<Product> products = productMapper.selectByExample(example);
    setFirstProductImage(products);
    setCategory(products);
    return products;
}
```

**3. 优化后的 setProduct**

```java
// 优化后：1+1 查询 (1次获取OrderItems + 1次批量获取Products)
private void setProduct(List<OrderItem> orderItems) {
    if (orderItems == null || orderItems.isEmpty()) {
        return;
    }
    // 收集所有产品ID
    List<Integer> pids = orderItems.stream()
            .map(OrderItem::getPid)
            .collect(Collectors.toList());

    // 一次性查询所有产品
    List<Product> products = productService.listByIds(pids);

    // 创建 Map 便于关联
    Map<Integer, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getId, p -> p));

    // 关联产品到订单项
    for (OrderItem orderItem : orderItems) {
        orderItem.setProduct(productMap.get(orderItem.getPid()));
    }
}
```

### 效果

- 10 个 OrderItem：10 次查询 → 2 次查询
- 查询次数从 N+1 降为 2 (1 次查 OrderItems + 1 次批量查 Products)
- 显著提升订单页面加载性能

---

## 7. 基础设施优化 - pom.xml

### 7.1 数据库连接池： Druid → HikariCP

**优化前**: Druid 连接池
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.18</version>
</dependency>
```

**优化后**: HikariCP (Spring Boot 默认，性能更优)
```xml
<!-- Spring Boot 默认使用 HikariCP，无需额外依赖 -->
```

**效果**:
- HikariCP 是目前性能最优的 JDBC 连接池
- Spring Boot 默认选择，开箱即用
- 配置更简洁

### 7.2 日志框架：Log4j → Logback

**优化前**: 显式使用 Log4j
```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.16</version>
</dependency>
```

**优化后**: Logback (Spring Boot 默认)
- 移除 Log4j 依赖
- 使用 Spring Boot 默认的 Logback
- 统一日志配置到 application.yml

### 7.3 开发工具：添加 DevTools 和 Actuator

**新增依赖**:
```xml
<!-- Actuator 监控 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- DevTools 热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

**Actuator 端点**:
- `/actuator/health` - 健康检查
- `/actuator/info` - 应用信息
- `/actuator/metrics` - 性能指标
- `/actuator/env` - 环境变量

**DevTools 特性**:
- 代码修改后自动重启
- 静态资源热加载
- 开发时即时生效

---

## 8. 并发优化

### 8.1 乐观锁 - 防止并发更新覆盖

**问题**: 多个请求同时读取同一数据，然后各自修改，后提交的会覆盖先提交的修改。

**解决方案**: 添加版本号字段，更新时检查版本

**1. Product.java 添加版本字段**

```java
/**
 * Version field for optimistic locking.
 * MyBatis will automatically check and increment this on updates.
 */
private Integer version;
```

**2. Mapper XML 添加乐观锁更新**

```xml
<!-- 乐观锁更新：仅当版本匹配时更新 -->
<update id="updateWithOptimisticLock">
    UPDATE product
    <set>
        <if test="name != null">name = #{name},</if>
        ...
        version = version + 1
    </set>
    WHERE id = #{id} AND version = #{version}
</update>
```

### 8.2 原子库存扣减 - 防止超卖

**问题**: 读-改-写 操作不是原子的，可能导致超卖（库存变为负数）

**解决方案**: 使用 SQL 原子扣减

**1. Mapper XML 添加原子扣减**

```xml
<!-- 原子库存扣减：返回影响行数，0表示库存不足 -->
<update id="decreaseStock">
    UPDATE product
    SET stock = stock - #{number}, version = version + 1
    WHERE id = #{pid} AND stock >= #{number}
</update>
```

**2. Service 层调用**

```java
@Override
public boolean decreaseStock(int pid, int number) {
    int rows = productMapper.decreaseStock(pid, number);
    return rows > 0;  // 返回 true 表示扣减成功
}
```

### 效果

- **乐观锁**: 防止并发更新时数据被覆盖
- **原子扣减**: 确保库存不会超卖，即使在高并发下也不会出现负库存

---

## 验证

所有优化已通过编译验证:

```bash
mvn clean compile
# BUILD SUCCESS
```

---

## 9. 缓存优化 - Redis + Spring Cache

### 9.1 概述

引入 Redis 作为缓存实现，使用 Spring Cache 简化缓存逻辑，提升热点数据的访问性能。

### 9.2 配置

**1. pom.xml 添加依赖**

```xml
<!-- Spring Boot Cache (缓存支持) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>

<!-- Redis (缓存实现) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

**2. application.yml Redis 配置**

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password:
    database: 0
    timeout: 3000
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
        max-wait: -1
```

**3. RedisCacheConfig.java 启用缓存**

```java
@Configuration
@EnableCaching
public class RedisCacheConfig {
}
```

### 9.3 缓存策略

| 缓存名称 | 服务类 | 缓存方法 | 缓存键 | 说明 |
|---------|--------|---------|-------|------|
| categories | CategoryServiceImpl | list() | "all" | 全部分类列表 |
| categories | CategoryServiceImpl | get(int id) | #id | 单个分类 |
| products | ProductServiceImpl | get(int id) | #id | 商品详情 |
| products | ProductServiceImpl | list(int cid) | "cid_" + cid | 按分类商品列表 |
| products | ProductServiceImpl | listByIds(List<Integer> ids) | #ids.hashCode() | 批量商品查询 |

### 9.4 缓存注解说明

**@Cacheable** - 缓存查询结果
```java
@Cacheable(value = "categories", key = "'all'")
public List<Category> list() { ... }
```

**@CacheEvict** - 数据变更时清除缓存
```java
@CacheEvict(value = "categories", allEntries = true)
public void add(Category category) { ... }
```

### 9.5 效果

- **分类数据缓存**: 导航栏分类列表、首页分类数据等热点数据直接从 Redis 获取
- **商品数据缓存**: 商品详情页、分类商品列表等高频访问页面响应更快
- **自动缓存失效**: 数据变更（增删改）时自动清除对应缓存
- **内存数据库**: Redis 基于内存，读取速度极快

---

## 后续优化建议

1. **数据库层面**
   - 为常用查询添加索引 (如 `order_item.pid`, `order_item.uid`)
   - 使用数据库级别的 SUM() 替代应用层循环
