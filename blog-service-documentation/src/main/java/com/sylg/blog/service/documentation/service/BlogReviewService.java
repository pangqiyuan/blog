package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.BlogReview;

import java.util.List;

public interface BlogReviewService {


    int insertOrUpdate(BlogReview record);

    int insertOrUpdateSelective(BlogReview record);

    void publishBlogByBlogId(BlogReview blogReview);

    List<String> findAllByIsPublishFalse();

    void delete(String id);

}

