package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.domain.BlogReview;
import com.sylg.blog.service.documentation.mapper.BlogReviewMapper;
import com.sylg.blog.service.documentation.service.BlogReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * BlogReview service impl
 * @author 忆地球往事
 */
@Service
@Slf4j
public class BlogReviewServiceImpl implements BlogReviewService {

    @Resource
    private BlogReviewMapper blogReviewMapper;

    @Override
    public int insertOrUpdate(BlogReview record) {
        return blogReviewMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(BlogReview record) {
        return blogReviewMapper.insertOrUpdateSelective(record);
    }

    @Override
    public void publishBlogByBlogId(BlogReview blogReview) {
        int i = blogReviewMapper.updateIsPublish(blogReview);
        if(i > 0){
            log.debug("博客{}审核完毕,审核结果{}",blogReview.getBlogId(),blogReview.getIsPublish());
        }
    }

    @Override
    public List<String> findAllByIsPublishFalse() {
        return Optional.ofNullable(blogReviewMapper.findAllByIsPublishFalse()).orElse(new ArrayList<>());
    }

    @Override
    public void delete(String id) {
        blogReviewMapper.deleteByBlogId(id);
    }
}

