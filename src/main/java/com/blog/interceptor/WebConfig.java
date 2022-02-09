package com.blog.interceptor;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description: 拦截器：加载适配器来实现拦截
 **/
//声明配置类
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor (new Lostpointercapture ())
                .addPathPatterns ("/admin/**")
                .excludePathPatterns ("/admin")
                .excludePathPatterns ("/admin/login");
    }
}
