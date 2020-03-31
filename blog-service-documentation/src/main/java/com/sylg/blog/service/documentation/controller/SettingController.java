package com.sylg.blog.service.documentation.controller;

import com.sylg.blog.service.documentation.common.constants.Constans;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pangqiyuan
 * @date 2019/12/10
 *
 * 用户信息设置的Controller
 */
@Controller
@RequestMapping(value = "setting")
public class SettingController {

    @GetMapping(value = "")
    public String setting(HttpServletRequest request){
        return "setting/index";

    }
}
