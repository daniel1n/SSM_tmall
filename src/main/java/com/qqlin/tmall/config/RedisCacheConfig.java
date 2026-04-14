package com.qqlin.tmall.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis 缓存配置类
 *
 * 功能说明:
 * - 启用 Spring Cache 缓存注解支持
 * - 配合 Spring Boot Starter Data Redis 使用
 *
 * 缓存策略:
 * - 分类数据 (Category): list() 方法结果缓存，热点数据加速访问
 * - 商品数据 (Product): get() 和 list(int cid) 方法结果缓存
 *
 * @author qqlin
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
}
