package com.sylg.blog.service.documentation.exception;

/**
 * @program: blog
 * @description: 邮箱异常
 * @author: 忆地球往事
 * @create: 2020-04-24 17:04
 **/
public class EmailException extends UserException {

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
