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
        int count = blogUserMapper.findByUserName(userName);
        return count;
    }

    @Override
    public BlogUser selectOneByLoginCode(String loginCode) {
        BlogUser blogUser = blogUserMapper.selectOneByLoginCode(loginCode);
        if(blogUser != null){
            return blogUser;
        }
        return null;
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
