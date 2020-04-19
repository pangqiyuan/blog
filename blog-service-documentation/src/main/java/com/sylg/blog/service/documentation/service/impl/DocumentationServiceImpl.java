package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.common.utils.BlogUtils;
import com.sylg.blog.service.documentation.common.utils.DateUtils;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.repository.DocRepository;
import com.sylg.blog.service.documentation.service.DocumentationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author pangqiyuan
 */
@Service
@Slf4j
public class DocumentationServiceImpl implements DocumentationService {

    @Resource
    private DocRepository docRepository;

    private final MongoTemplate mongoTemplate;

    public DocumentationServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Boolean saveCommentByDocId(String id, Documentation.CommentByBean commentByBean) {
        try {
            commentByBean.setCid(BlogUtils.randomUUIDWithoutDash());
            commentByBean.setCommentTime(DateUtils.nowFormat());
            log.debug("对博客{}进行评论，评论内容{}",id,commentByBean);
            Query query = new Query(Criteria.where("_id").is(id));
            Update update = new Update();
            update.addToSet("commentByBeans",commentByBean);
            mongoTemplate.upsert(query, update, Documentation.class);
        }catch (Exception e){
            log.error("博客{}评论保存异常，异常信息：{}",id ,e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean saveCommentByDocId(String id, Documentation.ReplyComment replyComment, String replyCid) {
        try {
            replyComment.setRid(BlogUtils.randomUUIDWithoutDash());
            replyComment.setCommentTime(DateUtils.nowFormat());
            log.debug("对评论{}进行回复，评论内容{}",replyCid,replyComment);
            Query query = new Query(Criteria.where("_id").is(id).and("commentByBeans.cid").is(replyCid));
            Update update = new Update();
            update.addToSet("commentByBeans.$.replyComment",replyComment);
            mongoTemplate.upsert(query, update, Documentation.class);
        }catch (Exception e){
            log.error("博客{}评论回复保存异常，异常信息：{}",id ,e);
            return false;
        }
        return true;
    }

    @Override
    public Documentation findDocById(String id) {

        Optional<Documentation> documentation = docRepository.findById(id);
        return documentation.orElse(null);
    }

    @Override
    public List<Documentation> findDocByIds(List<String> ids) {


        List<Documentation> documentations = null;
        try {
            if (!Objects.isNull(ids)) {
                log.debug("通过id{}查询blog",ids);
                documentations = (List<Documentation>) docRepository.findAllById(ids);
            }
        }catch (Exception e){
            log.error("插入标签异常{}",e.getMessage());
        }
        return documentations;
    }

    @Override
    public List<Documentation> findAllByName(String username) {
        return docRepository.findAllByUsername(username);
    }

    @Override
    public List<Documentation> findAll(Pageable pageable) {
        Page<Documentation> documentations = docRepository.findAll(pageable);
        return documentations.getContent();
    }

    @Override
    public void deleteDocByDocName(String docId) {
        docRepository.deleteById(docId);
    }

    @Override
    public void insertDoc(Documentation documentation){
        documentation.setCreateTime(DateUtils.nowFormat());
        documentation.setCommentSize(0);
        documentation.setScore(0d);
        docRepository.insert(documentation);
    }

    @Override
    public List<Documentation> findAll() {
        return docRepository.findAll();
    }

    @Override
    public Documentation findDocByDocName(String docName,String username) {
        return docRepository.findDocumentationByDocNameAndUsername(docName,username);
    }

    @Override
    public void saveDocByDocName(Documentation documentation){
        documentation.setModifyTime(DateUtils.nowFormat());
        docRepository.save(documentation);
    }

    @Override
    public List<Documentation> findDocByScoreDesc(Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "score");
        Query query = new Query();
        try {
            return mongoTemplate.find(query.with(sort).limit(limit), Documentation.class);
        }catch (Exception e){
            log.error("博客根据分数排序查询异常，异常信息：{}",e.getMessage());
            return null;
        }
    }

    @Override
    public List<Documentation> searchByKeyword(String keyword) {
        Pattern pattern = BlogUtils.getPattern(keyword);
        Sort sort = new Sort(Sort.Direction.DESC, "score");
        Query query = new Query(Criteria.where("docName").regex(pattern));
        List<Documentation> documentations = mongoTemplate.find(query.with(sort), Documentation.class);
        return Optional.of(documentations).orElse(new ArrayList<>());
    }

    @Override
    public List<Documentation> findDocByRandom(Integer randomCount) {
        try {
            TypedAggregation<Documentation> typedAggr = Aggregation.newAggregation(Documentation.class,
                    Aggregation.sample(randomCount));
            AggregationResults<Documentation> aggregate = mongoTemplate.aggregate(typedAggr, Documentation.class);
            return aggregate.getMappedResults();
        }catch (Exception e){
            log.error("博客随机查询异常，异常信息：{}",e.getMessage());
            return null;
        }
    }

    @Override
    public List<Documentation> findDocByCommentDesc(Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "commentSize");
        Query query = new Query();
        try {
            return mongoTemplate.find(query.with(sort).limit(limit), Documentation.class);
        }catch (Exception e){
            log.error("博客根据评论数排序查询异常，异常信息：{}",e.getMessage());
            return null;
        }
    }

    @Override
    public List<Documentation> findDocByRecommend(Integer limit) {
        Query query = new Query();
        try {
            return mongoTemplate.find(query.limit(limit), Documentation.class);
        }catch (Exception e){
            log.error("博客推荐查询异常，异常信息：{}",e.getMessage());
            return null;
        }
    }
}
