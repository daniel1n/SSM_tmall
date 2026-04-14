# SSM_tmall 升级说明

## 升级概览

| 项目 | 升级前 | 升级后 |
|------|--------|--------|
| Java 版本 | JDK 8 | JDK 17 |
| Spring 版本 | Spring 5.2.6 | Spring Boot 2.7.18 (内嵌 Spring 5.7) |
| 项目架构 | 传统 SSM (XML 配置) | Spring Boot (Java Config) |
| 服务器 | 外部 Tomcat | Spring Boot 内嵌 Tomcat |
| 打包方式 | war | jar |
| 构建工具 | Maven | Maven |

---

## 一、依赖变更 (pom.xml)

### 1.1 移除的依赖

```xml
<!-- 传统 SSM 依赖 (已不再使用) -->
<!-- Spring 独立依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.6.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.6.RELEASE</version>
</dependency>
<!-- ... 其他 Spring 独立依赖 -->

<!-- 旧版本 MyBatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.1.1</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>1.2.3</version>
</dependency>

<!-- 旧版本组件 -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.16</version>
</dependency>
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.2.1</version>
</dependency>
```

### 1.2 新增的依赖

```xml
<!-- Spring Boot Parent (自动管理版本) -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.18</version>
</parent>

<!-- Spring Boot Starter (一站式依赖) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
</dependency>

<!-- MyBatis Spring Boot Starter -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.2</version>
</dependency>

<!-- PageHelper Spring Boot Starter -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>1.4.7</version>
</dependency>

<!-- Druid Spring Boot Starter -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.2.18</version>
</dependency>

<!-- Commons Lang3 (替换 commons-lang) -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
```

---

## 二、配置变更

### 2.1 移除的 XML 配置文件

| 原有文件 | 说明 |
|----------|------|
| `web.xml` | Servlet 配置，已由 Spring Boot 自动处理 |
| `applicationContext.xml` | Spring 主配置，已迁移到 Java Config |
| `springMVC.xml` | Spring MVC 配置，已迁移到 Java Config |

### 2.2 新增的 Java 配置类

| 文件 | 说明 |
|------|------|
| `Application.java` | Spring Boot 启动类 |
| `config/MybatisConfig.java` | MyBatis 配置 (SqlSessionFactory, 事务) |
| `config/WebMvcConfig.java` | Spring MVC 配置 (视图解析器, 拦截器) |

### 2.3 新增的配置文件

**`application.yml`** - 替代原有的 `jdbc.properties`, `log4j.properties` 等

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/tmall_ssm?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: admin
    druid:
      initial-size: 5
      max-active: 20

mybatis:
  configuration:
    map-underscore-to-camel-case: true

server:
  port: 8080
```

---

## 三、代码变更

### 3.1 拦截器变更

**`LoginInterceptor.java`**
- `HandlerInterceptorAdapter` → `HandlerInterceptor`
- 移除 `@Autowired` 依赖注入
- 移除不必要的 `postHandle`, `afterCompletion` 方法

**`OtherInterceptor.java`**
- `HandlerInterceptorAdapter` → `HandlerInterceptor`
- 移除 `@Autowired`，改用构造器注入

### 3.2 工具类变更

| 类 | 变更 |
|----|------|
| `RandomUtils` | `org.apache.commons.lang.math.RandomUtils` → `org.apache.commons.lang3.RandomUtils` |
| `nextInt(10000)` | → `nextInt(0, 10000)` (commons-lang3 API 不同) |

### 3.3 包名变更

| 原包名 | 新包名 |
|--------|--------|
| `org.apache.commons.lang` | `org.apache.commons.lang3` |

---

## 四、运行方式变更

### 4.1 传统部署 (war)

```bash
# 打包 war 文件
mvn clean package
# 部署到外部 Tomcat
```

### 4.2 Spring Boot 部署 (jar)

```bash
# 打包可执行 jar
mvn clean package

# 运行
java -jar target/tmall_ssm.jar

# 或使用 Maven 插件
mvn spring-boot:run
```

---

## 五、编译验证

```bash
mvn clean compile
# 预期输出: BUILD SUCCESS
```

---

## 六、常见问题

### 6.1 编译错误: `package org.apache.commons.lang does not exist`

**原因**: 升级后 `commons-lang` 已被 `commons-lang3` 替代

**解决**: 将所有 `org.apache.commons.lang.*` 改为 `org.apache.commons.lang3.*`

### 6.2 编译错误: `HandlerInterceptorAdapter` 找不到

**原因**: `HandlerInterceptorAdapter` 在 Spring 5.3+ 已废弃

**解决**: 改用 `HandlerInterceptor` 接口，实现 `preHandle`, `postHandle`, `afterCompletion` 方法

### 6.3 编译错误: `RandomUtils.nextInt(int)` 方法不存在

**原因**: `commons-lang3` 的 `RandomUtils.nextInt()` 方法签名不同

**解决**:
```java
// 旧: RandomUtils.nextInt(10000)
// 新: RandomUtils.nextInt(0, 10000)
```

### 6.4 运行错误: 数据库连接失败

**原因**: MySQL 8.x 需要添加 `serverTimezone` 参数

**解决**: 在 `application.yml` 的 URL 中添加:
```yaml
url: jdbc:mysql://localhost:3306/tmall_ssm?serverTimezone=Asia/Shanghai
```

---

## 七、技术栈对照表

| 层级 | 传统 SSM | Spring Boot |
|------|----------|-------------|
| 核心框架 | Spring 5.2.6 | Spring Boot 2.7.18 (Spring 5.7) |
| Web 框架 | SpringMVC | Spring Boot Web (内嵌 Tomcat) |
| ORM | MyBatis 3.1.1 | MyBatis Spring Boot 2.2.2 |
| 分页 | PageHelper 5.1.2 | PageHelper Spring Boot 1.4.7 |
| 连接池 | Druid 1.0.18 | Druid Spring Boot Starter 1.2.18 |
| 日志 | Log4j 1.2.16 | Logback (Spring Boot 默认) |
| 构建 | Maven | Maven |
| Java 版本 | 8 | 17 |

---

## 八、后续优化建议

1. **数据库连接池**: 考虑使用 HikariCP (Spring Boot 默认，性能更优)
2. **日志框架**: 考虑使用 Logback/SLF4J 替代 Log4j
3. **Lombok**: 使用 `@Data`, `@Getter/@Setter` 简化 POJO 代码
4. ** Actuator**: 添加 `spring-boot-starter-actuator` 用于监控
5. **热部署**: 添加 `spring-boot-devtools` 开发时热加载
