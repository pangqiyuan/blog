package com.sylg.blog.service.documentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.sylg.blog.service.documentation.mapper")
@EnableAspectJAutoProxy
public class ServiceDocumentationApplication {


    public static void main(String[] args) {
        SpringApplication.run(ServiceDocumentationApplication.class,args);
    }
}
