package org.example.config;

import org.example.middleware.AuthMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 */
@Configuration
public class FilterConfig {

    @Autowired
    private AuthMiddleware authMiddleware;

    @Bean
    public FilterRegistrationBean<AuthMiddleware> authFilter() {
        FilterRegistrationBean<AuthMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authMiddleware);
        registrationBean.addUrlPatterns("/user/*", "/org/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}