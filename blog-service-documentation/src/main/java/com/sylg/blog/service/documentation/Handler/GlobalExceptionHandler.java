package com.sylg.blog.service.documentation.Handler;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * @program: blog
 * @description: 全局异常处理
 * @author: 忆地球往事
 * @create: 2020-03-27 12:47
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 方法参数校验
     */
    @ExceptionHandler(BindException.class)
    public Result handleMethodArgumentNotValidException(BindException e) {
        log.error(e.getMessage(), e);
        return BaseResult.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 用户异常处理
     */
    @ExceptionHandler(UserException.class)
    public ModelAndView userExceptionHandler(HttpServletRequest request, UserException e) {

        log.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",e.getUrl());
        mv.addObject("exception", e.getMessage());
        mv.setViewName("error/error");
        return mv;
    }

    /**
     * 异常处理
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {

        log.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url","/");
        mv.addObject("exception", e.getMessage());
        mv.setViewName("error/error");
        return mv;
    }
}
