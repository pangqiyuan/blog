package com.sylg.blog.service.documentation.mapper;

import com.sylg.blog.service.documentation.common.mapper.MyMapper;
import com.sylg.blog.service.documentation.domain.BlogReview;

import java.util.List;

/**
 * blog review service
 * @author 忆地球往事
 */
public interface BlogReviewMapper extends MyMapper<BlogReview> {
    int insertOrUpdate(BlogReview record);

    int insertOrUpdateSelective(BlogReview record);

    /**
     * 博客审核更新
     * @param blogReview 博客审核
     * @return 是否成功
     */
    int updateIsPublish(BlogReview blogReview);

    List<String> findAllByIsPublishFalse();

    int deleteByBlogId(String blogId);
}