package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.domain.Tag;
import com.sylg.blog.service.documentation.repository.TagRepository;
import com.sylg.blog.service.documentation.service.TagService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @program: blog
 * @description: tag service impl
 * @author: 忆地球往事
 * @create: 2020-04-18 19:15
 **/
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final MongoTemplate mongoTemplate;

    public TagServiceImpl(TagRepository tagRepository, MongoTemplate mongoTemplate) {
        this.tagRepository = tagRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Tag> findByIsMainTag() {
        List<Tag> tags = null;
        try {
            tags = tagRepository.findAllByIsMainTagIsTrue().orElse(new ArrayList<>());
        }catch (Exception e){
            log.error("查询主标签异常{}",e.getMessage());
        }
        return tags;
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = null;
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "blogNumber");
            tags = Optional.of(tagRepository.findAll(sort)).orElse(new ArrayList<>());
        }catch (Exception e){
            log.error("查询标签异常{}",e.getMessage());
        }
        return tags;
    }

    @Override
    public void insertTag(Tag tag) {
        try {
            tagRepository.insert(tag);
        } catch (Exception e) {
            log.error("插入标签异常{}",e.getMessage());
        }
    }

    @Override
    public void updateBlogId(String tid ,String blogId) {
        Query query = new Query(Criteria.where("_id").is(tid));
        Update update = new Update();
        update.addToSet("blogId",blogId);
        mongoTemplate.upsert(query, update, Tag.class);
    }

    @Override
    public List<String> findBlogIdByMainTag(String mainTag) {
        log.debug("通过标签{}查询文章ID" ,mainTag);
        Optional<Tag> tag = tagRepository.findByName(mainTag);
        return tag.map(Tag::getBlogId).orElse(null);
    }
}
