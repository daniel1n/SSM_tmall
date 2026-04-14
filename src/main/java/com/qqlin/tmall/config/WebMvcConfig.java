package com.qqlin.tmall.config;

import com.qqlin.tmall.interceptor.LoginInterceptor;
import com.qqlin.tmall.interceptor.OtherInterceptor;
import com.qqlin.tmall.service.CategoryService;
import com.qqlin.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置类
 *
 * 功能说明:
 * - 配置 JSP 视图解析器 (prefix: /WEB-INF/jsp/, suffix: .jsp)
 * - 配置静态资源处理
 * - 配置拦截器链
 *
 * 迁移说明:
 * - 替代原有的 springMVC.xml 配置文件
 * - 使用 Java Config 方式替代 XML 配置
 * - 拦截器作为 Spring Bean 注入，确保 @Autowired 依赖可用
 *
 * @author qqlin
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** 分类服务 - 用于填充导航栏分类 */
    @Autowired
    CategoryService categoryService;

    /** 订单项服务 - 用于计算购物车数量 */
    @Autowired
    OrderItemService orderItemService;

    /**
     * 添加拦截器配置
     *
     * 拦截器说明:
     * - LoginInterceptor: 登录状态拦截，保护需要登录的 /fore* 接口
     * - OtherInterceptor: 其他拦截，负责填充导航栏分类、购物车数量等公共数据
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器 - 保护 /fore* 端点
        // 注意: 拦截器通过构造器接收服务实例，而非 @Autowired
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/fore*")
                .excludePathPatterns(
                        "/forehome",
                        "/forecheckLogin",
                        "/forelogin",
                        "/foreloginAjax",
                        "/foreregister",
                        "/foreproduct",
                        "/forecategory",
                        "/foresearch"
                );

        // 其他拦截器 - 填充公共数据
        // 创建时注入依赖的服务
        registry.addInterceptor(new OtherInterceptor(categoryService, orderItemService))
                .addPathPatterns("/fore*");
    }

    /**
     * 配置静态资源处理
     *
     * @param registry 静态资源注册表
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源路径映射
        // /static/** -> classpath:/static/
        // /images/** -> file:./images/
    }
}
