package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.Tag;

import java.util.List;

/**
 * tag service
 * @author 忆地球往事
 */
public interface TagService {
    /**
     *  通过排序查询标签
     * @return tags
     */
    List<Tag> findAllBySort();

    /**
     * 插入标签
     * @param tag 标签
     */
    void insertTag(Tag tag);

    /**
     * 插入对应标签的博客id
     * @param tid 标签id
     * @param blogId 博客id
     */
    void updateBlogId(String tid ,String blogId);

    /**
     * 查询主标签对应的博客id
     * @param mainTag 主标签
     * @return 博客id
     */
    List<String> findBlogIdByMainTag(String mainTag);

    /**
     * 查询所有主标签
     * @return tags
     */
    List<Tag> findByIsMainTag();

    /**
     * 查询其他标签
     * @return tags
     */
    List<Tag> findAllIsNotMainTag();

    /**
     * 查询所有标签
     * @return tags
     */
    List<Tag> findAll();

    /**
     * 删除标签
     * @param tid 标签id
     */
    void delete(String tid);
}
