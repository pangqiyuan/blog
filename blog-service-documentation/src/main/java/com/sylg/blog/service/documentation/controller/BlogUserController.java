package com.sylg.blog.service.documentation.controller;


import com.sylg.blog.service.documentation.common.constants.Constans;
import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.service.BlogUserService;
import com.sylg.blog.service.documentation.service.MailService;
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

    @Resource
    private MailService mailService;


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
    * @return: com.sylg.blog.service.documentation.common.dto.Result
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
                    log.info("用户登录信息：{}",blogUser1);
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
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @PostMapping(value = "/register")
    public @ResponseBody Result register(BlogUser blogUser){
        Result result = new Result();
        int count = blogUserService.findCountByUserName(blogUser.getUserName());
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

    /**
    * @Description: 跳转到忘记密码界面
    * @Param: []
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/4
    */
    @GetMapping(value = "/find_password")
    public String findPassword(){
        return "find_password";
    }

    /**
    * @Description: 通过邮件发送确认用户
    * @Param: [blogUser, request]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/4
    */
    @PostMapping(value = "/find_password")
    public @ResponseBody Result findPasswordBy(@Validated BlogUser blogUser,HttpServletRequest request){
        BlogUser blogUser1 = blogUserService.selectOneByLoginCode(blogUser.getLoginCode());
        if(blogUser1 == null){
          return BaseResult.error("账号不存在");
        }else {
            //邮件发送
            mailService.sendAsyncHtmlMail(blogUser,blogUser1,"忆地球往事博客密码找回",request);

            log.info("临时设置用户账号:" + blogUser.getLoginCode());
            request.getSession().setAttribute("mailLoginCode",blogUser.getLoginCode());

            //若邮箱不同则更新
            if(blogUser1.getEmail() == null || !blogUser.getEmail().equals(blogUser1.getEmail()) ){
                    blogUserService.updateEmailByLoginCode(blogUser);
            }
            return BaseResult.success("邮件正在发送，请到邮箱查收");

        }
    }

    /**
    * @Description: 跳转到密保确认页面
    * @Param: []
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/7
    */
    @GetMapping(value="/pwdQuestion")
    public String pwdQuestion(ModelMap modelMap, HttpServletRequest request) {
        String mailLoginCode = (String) request.getSession().getAttribute("mailLoginCode");
        BlogUser blogUser = blogUserService.selectOneByLoginCode(mailLoginCode);
        modelMap.addAttribute("blogUser",blogUser);
        log.info("邮箱认证后，进行密保确认页面跳转");
        return "setting/ackPwdQuestion";
    }

    @PostMapping(value="/pwdQuestion")
    public @ResponseBody Result pwdQuestion(BlogUser blogUser, HttpServletRequest request) {
        String mailLoginCode = (String) request.getSession().getAttribute("mailLoginCode");
        boolean success = blogUserService.ackPwdQuestions(blogUser, mailLoginCode);
        log.info("临时设置用户{}密保状态:{}" , blogUser.getLoginCode(),success);
        request.getSession().setAttribute("pwdStatus",success);
        return success ? BaseResult.success("密保验证成功") : BaseResult.error("密保验证失败");
    }

    /**
     * @Description: 跳转忘记密码修改密码界面
     * @Param: []
     * @return: java.lang.String
     * @Author: 忆地球往事
     * @Date: 2020/3/29
     */
    @GetMapping(value="/email_password")
    public String emailPassword(HttpServletRequest request) {
        Boolean pwdStatus = (Boolean) request.getSession().getAttribute("pwdStatus");
        if (pwdStatus == null || !pwdStatus) {
            return "setting/ackPwdQuestion";
        }
        log.info("邮箱认证后，找回密码");
        return "setting/email_password";
    }


    /**
     * @Description: 用户修改密码
     * @Param: [newPassword = 新密码 , request]
     * @return: com.sylg.blog.service.documentation.common.dto.Result
     * @Author: 忆地球往事
     * @Date: 2020/3/29
     */
    @PostMapping(value="/email_password")
    public @ResponseBody Result emailPassword(String newPassword, HttpServletRequest request) {
        String mailLoginCode = (String) request.getSession().getAttribute("mailLoginCode");
            if(mailLoginCode != null){
                BlogUser blogUser = new BlogUser();
                blogUser.setLoginCode(mailLoginCode);
                blogUserService.updatePasswordByLoginCode(blogUser, newPassword);
                log.info("删除临时用户账号:" + mailLoginCode);
                request.getSession().removeAttribute("mailLoginCode");
                return BaseResult.success("密码修改成功");
            }
            return BaseResult.error("邮箱认证未通过，请重新认证");
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
        log.info("跳转修改密码界面");
        return "setting/password";
    }


    /**
    * @Description: 用户修改密码
    * @Param: [password = 旧密码 , newPassword = 新密码 , request]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    @PostMapping(value="/edit_password")
    public @ResponseBody Result editPassword(String password ,String newPassword, HttpServletRequest request) {
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
        return "redirect:/";
    }
}
