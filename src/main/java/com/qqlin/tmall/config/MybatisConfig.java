package com.qqlin.tmall.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis 配置类
 *
 * 功能说明:
 * - 配置 SqlSessionFactory
 * - 配置 PageHelper 分页插件
 * - 配置 Mapper 接口扫描路径
 * - 配置事务管理器
 *
 * 迁移说明:
 * - 替代原有的 applicationContext.xml 中 MyBatis 相关配置
 * - 使用 Java Config 方式替代 XML 配置
 *
 * @author qqlin
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.qqlin.tmall.mapper")
public class MybatisConfig {

    /**
     * 配置 SqlSessionFactory
     *
     * @param dataSource 数据源 (由 Druid 自动配置)
     * @return SqlSessionFactory
     * @throws Exception 创建过程中的异常
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        // 设置数据源
        sessionFactory.setDataSource(dataSource);

        // 设置 MyBatis 配置文件路径 (可选)
        // sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        // 设置 Mapper XML 文件路径
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mapper/**/*.xml")
        );

        // 配置 PageHelper 分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        pageInterceptor.setProperties(properties);
        sessionFactory.setPlugins(pageInterceptor);

        return sessionFactory.getObject();
    }

    /**
     * 配置事务管理器
     *
     * @param dataSource 数据源
     * @return 事务管理器
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
