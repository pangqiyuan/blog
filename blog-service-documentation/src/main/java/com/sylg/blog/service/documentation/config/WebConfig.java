package com.sylg.blog.service.documentation.config;


import com.sylg.blog.service.documentation.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: blog
 * @description: web config
 * @author: 忆地球往事
 * @create: 2020-04-24 16:29
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/blog/user/edit_password")
                .addPathPatterns("/setting/**")
                .addPathPatterns("/backTag/**")
                .addPathPatterns("/book/**")
                .addPathPatterns("/announcement/**")
                .addPathPatterns("/manager/**")
                .excludePathPatterns("/static/**");
    }

}
