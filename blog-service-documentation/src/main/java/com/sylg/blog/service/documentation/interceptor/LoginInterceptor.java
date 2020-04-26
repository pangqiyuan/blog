package com.sylg.blog.service.documentation.interceptor;

import com.sylg.blog.service.documentation.common.constants.Constans;
import com.sylg.blog.service.documentation.domain.BlogUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @program: blog
 * @description: 用户登录校验
 * @author: 忆地球往事
 * @create: 2020-04-24 16:22
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BlogUser blogUser = (BlogUser) request.getSession().getAttribute(Constans.CURRENT_USER);
        if (Objects.isNull(blogUser)){
            log.info("------跳转到login页面-----");
            response.sendRedirect(request.getContextPath()+"/blog/user/login");
            return false;
        }else {
            return true;
        }
    }
}
