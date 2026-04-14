package com.qqlin.tmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 应用启动类
 *
 * 仿天猫商城系统主入口
 *
 * 升级说明:
 * - 从传统 SSM (Spring + SpringMVC + MyBatis) 架构迁移到 Spring Boot 2.7.18
 * - 从 JDK 8 升级到 JDK 17
 * - 使用 Spring Boot 内嵌 Tomcat 服务器
 * - XML 配置迁移到 Java Config (配置类)
 *
 * @author qqlin
 * @since 2026-04-14
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
