package com.sylg.blog.service.documentation.exception;

/**
 * @program: blog
 * @description: pwd status exception
 * @author: 忆地球往事
 * @create: 2020-04-24 17:15
 **/
public class PwdStatusException extends UserException {


    public PwdStatusException(String message) {
        super(message);
    }

    public PwdStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
