# SSM_tmall 电商平台

基于 Spring Boot 2.7.18 架构的仿天猫商城系统。

## 项目概况

| 属性 | 值 |
|------|-----|
| GroupId | com.qqlin.tmall |
| ArtifactId | tmall_ssm |
| 版本 | 1.0-SNAPSHOT |
| 打包方式 | jar (可执行) |
| Java 版本 | 17 |
| Spring Boot | 2.7.18 |

## 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 核心框架 | Spring Boot | 2.7.18 (Spring 5.7) |
| Web 框架 | Spring Boot Web | 内嵌 Tomcat |
| ORM | MyBatis Spring Boot | 2.2.2 |
| 分页 | PageHelper Spring Boot | 1.4.7 |
| 数据库 | MySQL + Connector | 8.0.28 |
| 连接池 | HikariCP | Spring Boot 默认 |
| 缓存 | Redis + Spring Cache | Spring Boot Data Redis |
| 视图 | JSP (嵌入式 Tomcat) | - |
| 日志 | Logback (Spring Boot 默认) | - |
| 热部署 | DevTools | Spring Boot 默认 |
| 监控 | Actuator | Spring Boot 默认 |
| 代码简化 | Lombok | 1.18.30 |

## 项目结构

```
SSM_tmall/
├── pom.xml                          # Maven 配置 (Spring Boot)
├── src/main/java/com/qqlin/tmall/
│   ├── Application.java              # Spring Boot 启动类
│   ├── config/
│   │   ├── MybatisConfig.java       # MyBatis 配置
│   │   ├── RedisCacheConfig.java    # Redis 缓存配置
│   │   └── WebMvcConfig.java       # Spring MVC 配置
│   ├── comparator/                  # 商品排序比较器
│   ├── controller/                  # 控制器层
│   ├── interceptor/                 # 登录拦截器
│   ├── mapper/                      # MyBatis Mapper 接口
│   ├── pojo/                        # 实体类
│   ├── service/                     # 业务逻辑接口
│   └── util/                        # 工具类
├── src/main/resources/
│   ├── application.yml               # Spring Boot 配置
│   └── mapper/                      # MyBatis XML 映射文件
└── src/main/webapp/
    └── WEB-INF/jsp/                  # JSP 视图文件
```

## 升级历史

### JDK 8 → JDK 17 + Spring Boot 2.7.18

| 项目 | 升级前 | 升级后 |
|------|--------|--------|
| Java | 8 | 17 |
| Spring | 5.2.6 (传统 SSM) | 5.7 (Spring Boot 2.7.18) |
| 项目架构 | XML 配置 | Java Config |
| 服务器 | 外部 Tomcat | 内嵌 Tomcat |
| 打包 | war | jar |

**详细升级说明**: [UPGRADE.md](./UPGRADE.md)

**详细优化说明**: [OPTIMIZATION.md](./OPTIMIZATION.md)

## 数据模型

```
User ─────┬───── Order
          ├───── OrderItem (购物车项)
          └───── Review

Category ─────┬───── Product
              └───── Property

Product ──────┬───── ProductImage (商品图片)
              ├───── PropertyValue (属性值)
              ├───── OrderItem
              └───── Review

Property ──────── PropertyValue

Order ──────────── OrderItem
```

### 实体说明

| 实体 | 说明 |
|------|------|
| User | 用户 (name, password) |
| Category | 分类 (name) |
| Product | 商品 (name, subTitle, originalPrice, promotePrice, stock, version, createDate) |
| ProductImage | 商品图片 (type: type_single/type_detail) |
| Property | 属性 (名称，属于分类) |
| PropertyValue | 属性值 (关联商品和属性) |
| Order | 订单 (orderCode, address, post, receiver, mobile, status) |
| OrderItem | 订单项/购物车项 (number) |
| Review | 评价 (content, createDate) |

### 订单状态流转

```
waitPay (待支付) → waitDelivery (待发货) → waitConfirm (待确认) → waitReview (待评价) → finish (完成)
                                          ↓
                                    (可删除)
```

## 功能模块

### 买家端 (ForeController - /fore*)

| 功能 | 说明 |
|------|------|
| 商品浏览 | 首页、分类、搜索商品 |
| 用户注册 | /foreregister |
| 用户登录 | /forelogin, /forelogout |
| 商品详情 | /foreproduct |
| 购物车 | /forecart, /foreaddCart, /forechangeOrderItem, /foredeleteOrderItem |
| 下单 | /forebuyone, /forebuy, /forecreateOrder |
| 支付 | /forepayed |
| 订单管理 | /forebought, /foreconfirmPay, /foreorderConfirmed, /foredeleteOrder |
| 评价 | /forereview, /foredoreview |

### 管理后台 (其他 Controller)

| Controller | 功能 |
|------------|------|
| CategoryController | 分类管理 (CRUD) |
| ProductController | 商品管理 (CRUD) |
| PropertyController | 属性管理 (CRUD) |
| PropertyValueController | 属性值管理 |
| ProductImageController | 商品图片管理 |
| OrderController | 订单管理、发货 |
| UserController | 用户列表 |

## 服务层

| Service | 说明 |
|---------|------|
| CategoryService | 分类 CRUD |
| ProductService | 商品 CRUD、聚合查询、搜索 |
| ProductImageService | 商品图片管理 |
| PropertyService | 属性 CRUD |
| PropertyValueService | 属性值管理、自动初始化 |
| OrderService | 订单 CRUD、状态管理 |
| OrderItemService | 购物车项管理 |
| UserService | 用户 CRUD、认证 |
| ReviewService | 评价管理 |

## 排序比较器 (comparator)

| 类 | 排序方式 |
|----|----------|
| ProductPriceComparator | 按价格 |
| ProductDateComparator | 按上架时间 |
| ProductSaleCountComparator | 按销量 |
| ProductReviewComparator | 按评价数 |
| ProductAllComparator | 综合排序 |

## 工具类

| 类 | 说明 |
|----|------|
| Page | 分页工具 |
| ImageUtil | 图片处理 (转JPG、缩略图) |
| UploadedImageFile | 文件上传封装 |
| OverIsMergeablePlugin | MyBatis Generator 插件 |
| MybatisGenerator | 代码生成器 |

## 拦截器

- **LoginInterceptor**: 保护 `/fore*` 接口，允许游客访问首页、商品、搜索等
- **OtherInterceptor**: 填充导航栏分类、购物车数量

## 配置文件

### application.yml

数据库和框架配置统一在 `src/main/resources/application.yml` 中管理：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/tmall_ssm?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: admin

mybatis:
  configuration:
    map-underscore-to-camel-case: true

server:
  port: 8080
```

## 构建与运行

### 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 构建步骤

1. 创建数据库 `tmall_ssm`
2. 使用 Maven 构建:

```bash
mvn clean package
```

### 运行方式

**方式一: 直接运行 JAR**

```bash
java -jar target/tmall_ssm.jar
```

**方式二: 使用 Maven 插件**

```bash
mvn spring-boot:run
```

**方式三: 导入 IDE 运行**

将项目导入 IDEA/Eclipse，运行 `Application.java` 的 main 方法

### 访问地址

- 前台: http://localhost:8080/forehome
- 管理后台: http://localhost:8080/admin_category_list

## 优化特性

### 金额精度 - BigDecimal

使用 BigDecimal 替代 float/double 进行金额计算，避免浮点精度丢失：

- `Product`: `originalPrice`、`promotePrice` 改为 BigDecimal
- `Order`: `total` 改为 BigDecimal
- MyBatis Mapper JDBC 类型改为 DECIMAL

### N+1 查询问题修复

- `ProductService.listByIds()` 批量查询方法
- `OrderItemServiceImpl.setProduct()` 使用 Map 关联，避免循环查询

### 并发控制

- **乐观锁**: `Product.version` 字段 + `updateWithOptimisticLock`
- **原子库存扣减**: `ProductMapper.decreaseStock()` SQL 级库存校验

### 缓存优化 - Redis + Spring Cache

| 缓存名称 | 服务 | 缓存方法 | 说明 |
|---------|------|---------|------|
| categories | CategoryService | list(), get() | 分类数据缓存 |
| products | ProductService | get(), list(), listByIds() | 商品数据缓存 |

数据变更时自动通过 `@CacheEvict` 清除缓存。

## 依赖版本

| 依赖 | 版本 |
|------|------|
| Spring Boot | 2.7.18 |
| MyBatis Spring Boot | 2.2.2 |
| PageHelper Spring Boot | 1.4.7 |
| MySQL Connector | 8.0.28 |
| HikariCP | Spring Boot 默认 |
| Spring Data Redis | Spring Boot 默认 |
| Commons Lang3 | 3.x |
| Lombok | 1.18.30 |
