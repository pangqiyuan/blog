package com.sylg.blog.service.documentation.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sylg.blog.service.documentation.mapper.BlogUserMapper;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.service.BlogUserService;

@Service
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
    public int findByUserName(String userName){
        return blogUserMapper.findByUserName(userName);
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
    public BlogUser updatePasswordByLoginCode(BlogUser blogUser,String newPassword) {
        blogUser.setPassword(newPassword);
        BlogUser blogUser1 = null;
        int success = blogUserMapper.updatePasswordByLoginCode(blogUser);
        if(success > 0){
          blogUser1 = this.selectOneByLoginCode(blogUser.getLoginCode());
        }
        return blogUser1;
    }
}
