package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.common.constants.Constans;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sylg.blog.service.documentation.mapper.BlogUserMapper;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.service.BlogUserService;

@Service
@Slf4j
public class BlogUserServiceImpl implements BlogUserService{

    @Resource
    private BlogUserMapper blogUserMapper;
    
    /**
    * @Description: 新增用户
    * @Param: [record]
    * @return: int
    * @Author: 忆地球往事
    * @Date: 2020/3/16
    */
    @Override
    public int insertOrUpdate(BlogUser record) {
        return blogUserMapper.insertOrUpdate(record);
    }


    @Override
    public int insertOrUpdateSelective(BlogUser record) {
        return blogUserMapper.insertOrUpdateSelective(record);
    }

    @Override
    public void insert(BlogUser blogUser){
        blogUser.setMgrType("0");
        blogUserMapper.insert(blogUser);
    }

    @Override
    public int findCountByUserName(String userName){
        return blogUserMapper.findCountByUserName(userName);
    }

    @Override
    public BlogUser selectOneByLoginCode(String loginCode) {
        return blogUserMapper.selectOneByLoginCode(loginCode);
    }

    @Override
    public int updateEmailByLoginCode(BlogUser blogUser) {
        return blogUserMapper.updateEmailByLoginCode(blogUser);
    }

    @Override
    public boolean ackPwdQuestions(BlogUser blogUser,String loginCode) {
        BlogUser user = blogUserMapper.selectPwdQuestionsByLoginCode(loginCode);
        return blogUser.equals(user);
    }

    @Override
    public BlogUser updatePwdQuestionByLoginCode(HttpServletRequest request, BlogUser blogUser) {
        BlogUser newBlogUser = null;
        BlogUser oldBlogUser = (BlogUser) request.getSession().getAttribute(Constans.CURRENT_USER);
        blogUser.setLoginCode(oldBlogUser.getLoginCode());
        int success = blogUserMapper.updatePwdQuestionByLoginCode(blogUser);
        if(success > 0){
            newBlogUser = this.selectOneByLoginCode(blogUser.getLoginCode());
            log.info("确定密保问题后新的用户信息 : {}",newBlogUser);
            request.getSession().setAttribute(Constans.CURRENT_USER,newBlogUser);
        }
        return newBlogUser;
    }

    @Override
    public BlogUser updatePasswordByLoginCode(BlogUser blogUser,String newPassword) {
        blogUser.setPassword(newPassword);
        BlogUser blogUser1 = null;
        int success = blogUserMapper.updatePasswordByLoginCode(blogUser);
        if(success > 0){
          blogUser1 = this.selectOneByLoginCode(blogUser.getLoginCode());
          log.info("更新密码后新的用户信息 : {}",blogUser1);
        }
        return blogUser1;
    }
}
