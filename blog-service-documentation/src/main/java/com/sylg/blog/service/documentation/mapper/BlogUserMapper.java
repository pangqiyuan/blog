package com.sylg.blog.service.documentation.mapper;

import com.sylg.blog.service.documentation.common.mapper.MyMapper;
import com.sylg.blog.service.documentation.domain.BlogUser;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pangqiyuan
 * @date 2019/3/29
 *
 * 用户数据层
 */

@Repository
public interface BlogUserMapper extends MyMapper<BlogUser> {
    int insertOrUpdate(BlogUser record);

    int insertOrUpdateSelective(BlogUser record);

    int findCountByUserName(String username);

    BlogUser selectOneByLoginCode(String loginCode);

    int updateEmailByLoginCode(BlogUser blogUser);


    int updatePasswordByLoginCode(BlogUser blogUser);

    int updatePwdQuestionByLoginCode(BlogUser blogUser);
}