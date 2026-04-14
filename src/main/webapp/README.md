# Webapp 目录结构说明

本文档描述 `src/main/webapp` 目录的组织结构和各组件的功能。

## 目录结构

```
webapp/
├── WEB-INF/
│   ├── jsp/
│   │   ├── fore/                    # 前台页面 (买家端)
│   │   │   ├── home.jsp             # 首页
│   │   │   ├── product.jsp          # 商品详情页
│   │   │   ├── category.jsp         # 分类商品页
│   │   │   ├── cart.jsp            # 购物车页
│   │   │   ├── login.jsp           # 登录页
│   │   │   ├── register.jsp        # 注册页
│   │   │   ├── registerSuccess.jsp # 注册成功页
│   │   │   ├── searchResult.jsp     # 搜索结果页
│   │   │   ├── bought.jsp          # 已购买商品页
│   │   │   ├── buy.jsp             # 确认订单页
│   │   │   ├── confirmPay.jsp      # 确认支付页
│   │   │   ├── payed.jsp           # 支付成功页
│   │   │   ├── orderConfirmed.jsp   # 确认收货页
│   │   │   ├── review.jsp          # 商品评价页
│   │   │   └── alipay.jsp         # 支付宝支付页
│   │   │
│   │   ├── admin/                  # 后台管理页面
│   │   │   ├── listCategory.jsp    # 分类管理列表
│   │   │   ├── listProduct.jsp     # 商品管理列表
│   │   │   ├── listProperty.jsp    # 属性管理列表
│   │   │   ├── listProductImage.jsp # 商品图片管理
│   │   │   ├── listOrder.jsp       # 订单管理列表
│   │   │   ├── listUser.jsp        # 用户列表
│   │   │   ├── editCategory.jsp    # 编辑分类
│   │   │   ├── editProduct.jsp     # 编辑商品
│   │   │   ├── editProperty.jsp     # 编辑属性
│   │   │   ├── editPropertyValue.jsp # 编辑属性值
│   │   │   └── index.jsp           # 管理后台首页
│   │   │
│   │   └── include/                # JSP 片段 (可复用组件)
│   │       ├── fore/              # 前台页面片段
│   │       │   ├── header.jsp     # 前台公共头部 (CSS/JS引入)
│   │       │   ├── footer.jsp     # 前台公共底部
│   │       │   ├── top.jsp        # 顶部导航栏
│   │       │   ├── search.jsp      # 搜索框组件
│   │       │   ├── simpleSearch.jsp # 简化搜索框
│   │       │   ├── modal.jsp       # 模态框组件
│   │       │   ├── home/          # 首页专用片段
│   │       │   │   ├── homePage.jsp              # 首页主体
│   │       │   │   ├── categoryMenu.jsp           # 分类导航菜单
│   │       │   │   ├── carousel.jsp              # 轮播图组件
│   │       │   │   ├── categoryAndcarousel.jsp   # 分类+轮播组合
│   │       │   │   ├── homepageCategoryProducts.jsp # 首页分类商品
│   │       │   │   └── productsAsideCategorys.jsp  # 分类侧边商品
│   │       │   ├── product/    # 商品页专用片段
│   │       │   │   ├── productPage.jsp    # 商品页主体
│   │       │   │   ├── imgAndInfo.jsp     # 商品图片与信息
│   │       │   │   ├── productDetail.jsp  # 商品详情
│   │       │   │   └── productReview.jsp  # 商品评价
│   │       │   ├── category/   # 分类页专用片段
│   │       │   │   ├── categoryPage.jsp      # 分类页主体
│   │       │   │   ├── productsByCategory.jsp # 分类下商品
│   │       │   │   └── sortBar.jsp          # 排序栏
│   │       │   ├── cart/      # 购物车页专用片段
│   │       │   │   ├── cartPage.jsp         # 购物车主体
│   │       │   │   ├── buyPage.jsp          # 结算页面
│   │       │   │   ├── confirmPayPage.jsp   # 确认支付页面
│   │       │   │   ├── payedPage.jsp        # 支付成功页面
│   │       │   │   ├── orderConfirmedPage.jsp # 确认收货页面
│   │       │   │   ├── boughtPage.jsp       # 已购买页面
│   │       │   │   └── reviewPage.jsp       # 评价页面
│   │       │   ├── loginPage.jsp    # 登录表单片段
│   │       │   ├── registerPage.jsp # 注册表单片段
│   │       │   ├── registerSuccessPage.jsp # 注册成功片段
│   │       │   ├── searchResultPage.jsp    # 搜索结果页面
│   │       │   ├── productsBySearch.jsp    # 搜索商品列表
│   │       │   └── search.jsp       # 搜索组件
│   │       │
│   │       └── admin/      # 后台管理片段
│   │           ├── adminHeader.jsp  # 管理后台头部
│   │           ├── adminFooter.jsp  # 管理后台底部
│   │           ├── adminNavigator.jsp # 管理后台导航
│   │           └── adminPage.jsp    # 分页组件
│   │
├── admin/                   # 后台管理静态资源
│   ├── index.jsp           # 后台首页
│   └── style.css          # 后台管理样式
│
├── css/                    # 样式表
│   ├── bootstrap/         # Bootstrap 框架
│   │   └── 3.3.6/        # Bootstrap 3.3.6 版本
│   │       └── bootstrap.min.css
│   ├── fore/              # 前台样式
│   │   └── style.css     # 前台通用样式
│   └── back/             # 后台样式
│       └── style.css     # 后台管理样式
│
├── js/                    # JavaScript 脚本
│   ├── bootstrap/         # Bootstrap JS
│   │   └── 3.3.6/        # Bootstrap 3.3.6 版本
│   │       └── bootstrap.min.js
│   └── jquery/           # jQuery 库
│       └── 2.0.0/       # jQuery 2.0.0 版本
│           └── jquery.min.js
│
└── img/                   # 图片资源
    ├── category/         # 分类图片 (按分类ID命名: 1.jpg, 2.jpg...)
    ├── lunbo/           # 轮播图图片
    ├── productDetail/   # 商品详情图片
    ├── productSingle/   # 商品主图 (大图)
    ├── productSingle_middle/ # 商品主图 (中图)
    ├── productSingle_small/  # 商品主图 (小图)
    └── site/            # 网站通用图标
        └── star/        # 星级评价图标
```

---

## 页面模块说明

### 前台页面 (fore)

前台采用模块化 JSP 片段组合方式，通过 `<%@include %>` 引入公共组件。

| 页面 | 文件 | 说明 |
|------|------|------|
| 首页 | home.jsp | 组合 header + top + search + homePage + footer |
| 商品详情 | product.jsp | 组合 header + top + simpleSearch + productPage + footer |
| 分类商品 | category.jsp | 分类页面 + 商品列表 + 排序栏 |
| 购物车 | cart.jsp | 购物车商品列表管理 |
| 确认订单 | buy.jsp | 选择地址、支付方式等 |
| 登录/注册 | login.jsp, register.jsp | 用户认证 |

### 后台管理页面 (admin)

| 页面 | 文件 | 功能 |
|------|------|------|
| 分类管理 | listCategory.jsp | 分类CRUD、属性/商品跳转 |
| 商品管理 | listProduct.jsp | 商品CRUD |
| 属性管理 | listProperty.jsp | 属性CRUD |
| 属性值管理 | editPropertyValue.jsp | 商品属性值编辑 |
| 商品图片 | listProductImage.jsp | 商品主图/详情图管理 |
| 订单管理 | listOrder.jsp | 订单列表、发货操作 |
| 用户管理 | listUser.jsp | 用户列表查看 |

---

## JSP 片段说明

### 前台公共片段

| 片段 | 路径 | 功能 |
|------|------|------|
| header | include/fore/header.jsp | 引入 jQuery、Bootstrap、样式表，定义 formatMoney、checkEmpty 等工具函数 |
| footer | include/fore/footer.jsp | 页面底部版权信息 |
| top | include/fore/top.jsp | 顶部导航栏 (登录/注册/购物车) |
| search | include/fore/search.jsp | 搜索框组件 |
| modal | include/fore/modal.jsp | 登录模态框 |

### 首页片段

| 片段 | 路径 | 功能 |
|------|------|------|
| homePage | include/fore/home/homePage.jsp | 首页主体容器 |
| categoryMenu | include/fore/home/categoryMenu.jsp | 左侧分类导航菜单 |
| carousel | include/fore/home/carousel.jsp | 轮播图组件 |
| categoryAndcarousel | include/fore/home/categoryAndcarousel.jsp | 分类+轮播组合区域 |
| homepageCategoryProducts | include/fore/home/homepageCategoryProducts.jsp | 首页分类商品展示 |
| productsAsideCategorys | include/fore/home/productsAsideCategorys.jsp | 分类侧边推荐商品 |

### 商品页片段

| 片段 | 路径 | 功能 |
|------|------|------|
| productPage | include/fore/product/productPage.jsp | 商品页主体容器 |
| imgAndInfo | include/fore/product/imgAndInfo.jsp | 商品图片与价格信息 |
| productDetail | include/fore/product/productDetail.jsp | 商品详细描述 |
| productReview | include/fore/product/productReview.jsp | 商品评价列表 |

---

## 静态资源说明

### CSS 框架

- **Bootstrap 3.3.6**: 响应式前端框架，提供栅格系统、组件样式
- **jQuery 2.0.0**: JavaScript 函数库，AJAX 和 DOM 操作
- **fore/style.css**: 前台页面自定义样式
- **back/style.css**: 后台管理自定义样式

### 图片目录

| 目录 | 说明 |
|------|------|
| category/ | 分类图标，按 ID 命名 (1.jpg, 2.jpg...) |
| lunbo/ | 首页轮播图 |
| productSingle/ | 商品主图大图 640x640 |
| productSingle_middle/ | 商品主图中图 100x100 |
| productSingle_small/ | 商品主图小图 50x50 |
| productDetail/ | 商品详情页展示图片 |
| site/ | 网站公共图标 (箭头、星级等) |

---

## 前端技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| jQuery | 2.0.0 | DOM 操作、AJAX 请求 |
| Bootstrap | 3.3.6 | 响应式布局、UI 组件 |
| JSP | - | 视图模板 |
| JSTL | - | JSP 标签库 |

---

## 页面引用关系

```
首页 (home.jsp)
├── header.jsp (jQuery/Bootstrap引入)
├── top.jsp (导航栏)
├── search.jsp (搜索框)
├── homePage.jsp
│   ├── categoryMenu.jsp (左侧分类)
│   ├── carousel.jsp (轮播图)
│   └── homepageCategoryProducts.jsp
│       └── productsAsideCategorys.jsp
└── footer.jsp

商品页 (product.jsp)
├── header.jsp
├── top.jsp
├── simpleSearch.jsp
├── productPage.jsp
│   ├── imgAndInfo.jsp (图片+购买)
│   ├── productDetail.jsp
│   └── productReview.jsp
└── footer.jsp
```

---

## 前后台页面区分

| 特征 | 前台 (fore) | 后台 (admin) |
|------|------------|--------------|
| 访问路径 | /fore* | /admin* |
| 样式文件 | css/fore/style.css | css/back/style.css |
| 头部片段 | include/fore/header.jsp | include/admin/adminHeader.jsp |
| 布局风格 | 响应式、商城风格 | 表格列表、管理风格 |
