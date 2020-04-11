package com.sylg.blog.service.documentation.controller;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.service.BlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pangqiyuan
 * @date 2019/12/10
 *
 * 用户信息设置的Controller
 */
@Controller
@RequestMapping(value = "/setting")
public class SettingController {

    @Resource
    private BlogUserService blogUserService;

    @GetMapping(value = "")
    public String setting(){
        return "setting/index";

    }

    @GetMapping(value = "/pwdQuestion")
    public String pwdQuestion(){
        return "setting/password_questions";
    }

    @PostMapping(value = "/pwdQuestion")
    public @ResponseBody Result pwdQuestion(BlogUser blogUser, HttpServletRequest request){
        BlogUser user = blogUserService.updatePwdQuestionByLoginCode(request, blogUser);
        if (user != null){
            return BaseResult.success("密保问题设置成功");
        }
        return BaseResult.error("密保问题设置失败，请重新设置");
    }



}
