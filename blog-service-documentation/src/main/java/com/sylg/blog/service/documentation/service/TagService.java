package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.Tag;

import java.util.List;

public interface TagService {

    List<Tag> findAll();

    void insertTag(Tag tag);

    void updateBlogId(String tid ,String blogId);

    List<String> findBlogIdByMainTag(String mainTag);

    List<Tag> findByIsMainTag();
}
