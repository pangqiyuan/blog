package com.sylg.blog.service.documentation.exception;

/**
 * @program: blog
 * @description: user exception
 * @author: 忆地球往事
 * @create: 2020-04-16 18:00
 **/
public abstract class UserException extends RuntimeException {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
