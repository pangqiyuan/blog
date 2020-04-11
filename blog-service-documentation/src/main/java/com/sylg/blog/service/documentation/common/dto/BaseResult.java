package com.sylg.blog.service.documentation.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *  定义一个返回类型为json类型的基类
 */
@Data
public class BaseResult implements Serializable {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public static Result error(String message){
        return createResult(ERROR,message);
    }

    public static Result success(String message){
       return createResult(SUCCESS,message);
    }

    private static Result createResult(String code,String message){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(message);
        return result;
    }

}
