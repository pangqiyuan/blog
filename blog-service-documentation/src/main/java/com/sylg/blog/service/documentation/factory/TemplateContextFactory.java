package com.sylg.blog.service.documentation.factory;

import com.sylg.blog.service.documentation.common.dto.TemplateContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @program: blog
 * @description: Thymeleaf context构造工厂
 * @author: 忆地球往事
 * @create: 2020-03-27 22:40
 **/
@Component
@Slf4j
public class TemplateContextFactory {

    private static final String PASSWORD_URL = "http://localhost:8081/blog/user/pwdQuestion";


    @Autowired
    private TemplateEngine templateEngine;

    /**
    * @Description: 发送mail为模板的内容
    * @Param: [templateContext]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    public String process(TemplateContext templateContext){
        Context context = findPasswordContext(templateContext);
        return templateEngine.process("mail.html", context);
    }

    private Context findPasswordContext(TemplateContext templateContext){
        templateContext.setUrl(PASSWORD_URL);
        return createContext(templateContext);
    }

    /**
    * @Description: 通过反射封装Context
    * @Param: [templateContext]
    * @return: org.thymeleaf.context.Context
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    private Context createContext(TemplateContext templateContext){
        Context context = new Context();
        Field[] declaredFields = templateContext.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            //获取所有字段的get方法名
            String field = "get" + declaredField.getName().substring(0,1).toUpperCase() + declaredField.getName().substring(1);
            try {
                Method method = templateContext.getClass().getMethod(field);
                String value = (String)method.invoke(templateContext);
                log.info("邮件发送字段" + declaredField.getName() + " = " + value);
                context.setVariable(declaredField.getName(),value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return context;
    }
}
