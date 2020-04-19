package com.sylg.blog.service.documentation.exception;

/**
 * @program: blog
 * @description: blog score exception
 * @author: 忆地球往事
 * @create: 2020-04-16 18:00
 **/
public class BlogScoreException extends RuntimeException {

    public BlogScoreException(String message) {
        super(message);
    }

    public BlogScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
