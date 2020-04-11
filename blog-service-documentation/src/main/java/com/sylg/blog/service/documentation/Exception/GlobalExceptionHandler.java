package com.sylg.blog.service.documentation.Exception;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
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

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        log.error("Requst URL : {}，Exception : {}", request.getRequestURL(),e);

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
