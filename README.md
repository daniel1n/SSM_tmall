# SSM_tmall 电商平台

基于 SSM (Spring + SpringMVC + MyBatis) 架构的仿天猫商城系统。

## 项目概况

| 属性 | 值 |
|------|-----|
| GroupId | com.qqlin.tmall |
| ArtifactId | tmall_ssm |
| 版本 | 1.0-SNAPSHOT |
| 打包方式 | war |
| Java 版本 | 1.8 |

## 技术栈

- **Spring** 5.2.6.RELEASE
- **SpringMVC** 5.2.6.RELEASE
- **MyBatis** 3.5.6 + MyBatis-Spring 1.2.3
- **MySQL** 5.x
- **Druid** 1.18 (数据库连接池)
- **PageHelper** 5.1.2-beta (分页插件)
- **JSP** (视图层)
- **Log4j** 1.2.16

## 项目结构

```
SSM_tmall/
├── pom.xml
├── src/main/java/com/qqlin/tmall/
│   ├── comparator/          # 商品排序比较器
│   ├── controller/          # 控制器层
│   ├── interceptor/         # 登录拦截器
│   ├── mapper/              # MyBatis Mapper 接口
│   ├── pojo/                # 实体类
│   ├── service/            # 业务逻辑接口
│   └── util/               # 工具类
├── src/main/resources/
│   ├── applicationContext.xml   # Spring 主配置
│   ├── springMVC.xml            # Spring MVC 配置
│   ├── jdbc.properties          # 数据库连接配置
│   ├── log4j.properties        # 日志配置
│   └── mapper/                  # MyBatis XML 映射文件
└── src/main/webapp/WEB-INF/web.xml
```

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
| Product | 商品 (name, subTitle, originalPrice, promotePrice, stock, createDate) |
| ProductImage | 商品图片 (type: type_single/type_detail) |
| Property | 属性 (名称，屬于分类) |
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

## 数据库配置

编辑 `src/main/resources/jdbc.properties`:

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/tmall_ssm?useUnicode=true&characterEncoding=utf8
jdbc.username=root
jdbc.password=admin
```

## 构建与运行

1. 创建数据库 `tmall_ssm`
2. 修改 `jdbc.properties` 中的数据库配置
3. 使用 MyBatis Generator 生成 Mapper (或使用已有代码)
4. 使用 Maven 构建:

```bash
mvn clean package
```

5. 部署 war 包到 Tomcat

## 依赖版本

- Spring: 5.2.6.RELEASE
- MyBatis: 3.5.6
- MyBatis-Spring: 1.2.3
- MySQL Connector: 5.1.6
- Druid: 1.18
- PageHelper: 5.1.2-beta
- Servlet API: 4.0.1
