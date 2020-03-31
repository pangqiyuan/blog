package com.sylg.blog.service.documentation.controller;


import com.sun.xml.internal.bind.v2.TODO;
import com.sylg.blog.service.documentation.common.constants.Constans;
import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.common.utils.Result;
import com.sylg.blog.service.documentation.service.BlogUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pangqiyuan
 * @date 2019/12/10
 *
 * 用户登录和注册的Controller
 */
@Controller
@RequestMapping(value = "/blog/user")
@CrossOrigin
@Slf4j
public class BlogUserController {

    @Resource
    private BlogUserService blogUserService;

    /**
    * @Description: 用户登录页面跳转
    * @Param: []
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @GetMapping(value = "/login")
    public String  login() {
        return "login";
    }

    /**
    * @Description: 用户登录
    * @Param: [blogUser, request]
    * @return: com.sylg.blog.service.documentation.common.utils.Result
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @PostMapping(value = "/login")
    public @ResponseBody Result login(BlogUser blogUser, HttpServletRequest request){
        Result result = new Result();
        BlogUser blogUser1 = blogUserService.selectOneByLoginCode(blogUser.getLoginCode());
        if(blogUser1 == null){
            result.setCode(BaseResult.ERROR);
            result.setMsg("账号不存在");
        }
        else {
                if(blogUser.getPassword().equals(blogUser1.getPassword())){
                    request.getSession().setAttribute(Constans.CURRENT_USER ,blogUser1);
                    result.setCode(BaseResult.SUCCESS);
                }
                else {
                    result.setCode(BaseResult.ERROR);
                    result.setMsg("密码错误");
                }
        }

        return result;
    }

    /**
    * @Description: 用户注册界面跳转
    * @Param: []
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @GetMapping(value = "/register")
    public String registered(){
        return "register";
    }

    /**
    * @Description: 用户注册
    * @Param: [blogUser]
    * @return: com.sylg.blog.service.documentation.common.utils.Result
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @PostMapping(value = "/register")
    public @ResponseBody Result register(BlogUser blogUser){
        Result result = new Result();
        int count = blogUserService.findByUserName(blogUser.getUserName());
        BlogUser blogUser1 = blogUserService.selectOneByLoginCode(blogUser.getLoginCode());
        if(null != blogUser1){
            result.setCode(BaseResult.ERROR);
            result.setMsg("账号已存在");
        }
        else if (count > 0){
            result.setCode(BaseResult.ERROR);
            result.setMsg("用户名已存在");
        }
        else{
            blogUserService.insert(blogUser);
            result.setCode(BaseResult.SUCCESS);
        }
        return result;
    }

    @GetMapping(value = "/find_password")
    public String findPassword(){
        return "find_password";
    }

    @PostMapping(value = "/find_password")
    public @ResponseBody Result findPasswordBy(@Validated BlogUser blogUser){
        BlogUser blogUser1 = blogUserService.selectOneByLoginCode(blogUser.getLoginCode());
        if(blogUser1 == null){
          return BaseResult.error("账号不存在");
        }else {
            if(blogUser1.getEmail() == null || !blogUser.getEmail().equals(blogUser1.getEmail()) ){
                    blogUserService.updateEmailByLoginCode(blogUser);
            }
            //TODO 邮件发送
            return BaseResult.success("邮件正在发送，请到邮箱查收");

        }
    }

    /**
    * @Description: 跳转修改密码界面
    * @Param: []
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @GetMapping(value="/edit_password")
    public String  editPassword() {
        log.info("找回密码");
        return "setting/password";
    }

    /**
    * @Description: 用户修改密码
    * @Param: [password = 旧密码 , newPassword = 新密码 , request]
    * @return: com.sylg.blog.service.documentation.common.utils.Result
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @PostMapping(value="/edit_password")
    public @ResponseBody Result editPassword(String password ,String newPassword, HttpServletRequest request) {
        Result result = new Result();
        BlogUser blogUser = (BlogUser) request.getSession().getAttribute(Constans.CURRENT_USER);
        if(blogUser == null){
            return BaseResult.error("您未登录，请先登录后修改密码");
        }
        else if(blogUser.getPassword().equals(password)){
            BlogUser blogUser1 = blogUserService.updatePasswordByLoginCode(blogUser, newPassword);
            request.getSession().setAttribute(Constans.CURRENT_USER,blogUser1);
            log.info("用户信息：{}",request.getSession().getAttribute(Constans.CURRENT_USER));
            return BaseResult.success("密码修改成功");
        }else{
            return BaseResult.error("原密码不正确，请重新输入");
        }
    }

    /**
    * @Description: 用户注销
    * @Param: [request]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @GetMapping(value="/logout")
    public String logout(HttpServletRequest request){
        request.getSession().setAttribute(Constans.CURRENT_USER, null);
        return "redirect:/index";
    }
}
