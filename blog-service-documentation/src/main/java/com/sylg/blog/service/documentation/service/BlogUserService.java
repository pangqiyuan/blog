package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.BlogUser;

/**
 *
 * @author pangqiyuan
 * @date 2019/3/29
 *
 * 用户服务
 */
public interface BlogUserService{

    
    int insertOrUpdate(BlogUser record);

    int insertOrUpdateSelective(BlogUser record);
    
    /**
    * @Description: 添加用户
    * @Param: [blogUser]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    void insert(BlogUser blogUser);

    /**
    * @Description: 根据用户名查找用户
    * @Param: [userName]
    * @return: int
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    int findByUserName(String userName);

    /**
    * @Description: 根据账号查找用户
    * @Param: [loginCode]
    * @return: com.sylg.blog.service.documentation.domain.BlogUser
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    BlogUser selectOneByLoginCode(String loginCode);

    /**
    * @Description: 根据账号更新用户邮箱
    * @Param: [blogUser]
    * @return: int
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    int updateEmailByLoginCode(BlogUser blogUser);

    /**
    * @Description: 根据账号更新用户密码
    * @Param: [blogUser]
    * @return: int
    * @Author: 忆地球往事
    * @Date: 2020/3/29
    */
    BlogUser updatePasswordByLoginCode(BlogUser blogUser,String newPassword);
}
