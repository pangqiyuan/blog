package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.common.utils.BlogUtils;
import com.sylg.blog.service.documentation.common.utils.DateUtils;
import com.sylg.blog.service.documentation.domain.BlogReview;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.domain.Tag;
import com.sylg.blog.service.documentation.mapper.BlogReviewMapper;
import com.sylg.blog.service.documentation.repository.DocRepository;
import com.sylg.blog.service.documentation.service.DocumentationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * documentation service impl
 * @author pangqiyuan
 */
@Service
@Slf4j
public class DocumentationServiceImpl implements DocumentationService {

    @Resource
    private DocRepository docRepository;

    @Resource
    private BlogReviewMapper blogReviewMapper;

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

        Optional<Documentation> documentation = docRepository.findByIdAndIsPublishTrue(id);
        return documentation.orElse(null);
    }

    @Override
    public List<Documentation> findDocByIdsAndIsPublishTrue(List<String> ids) {


        List<Documentation> documentations = null;
        try {
            if (!Objects.isNull(ids)) {
                log.debug("通过id{}查询blog",ids);
                Query query = new Query(new Criteria("_id").in(ids).and("isPublish").is(true));
                documentations = mongoTemplate.find(query, Documentation.class);
            }
        }catch (Exception e){
            log.error("查询发布博客异常{}",e.getMessage());
        }
        return documentations;
    }

    @Override
    public List<Documentation> findDocByIds(List<String> ids) {
        List<Documentation> documentations = null;
        try {
              documentations = (List<Documentation>) docRepository.findAllById(ids);
        }catch (Exception e){
            log.error("批量查询博客异常{}",e.getMessage());
        }
        return documentations;
    }

    @Override
    public List<Documentation> findDocByIdsAndViewsSort(List<String> ids,Integer limit) {
        List<Documentation> documentations = null;
        try {
            if (!Objects.isNull(ids)) {
                log.debug("通过id{}查询blog",ids);
                Sort sort = new Sort(Sort.Direction.DESC, "views");
                Query query = new Query(new Criteria("_id").in(ids).and("isPublish").is(true));
                documentations = mongoTemplate.find(query.with(sort).limit(limit), Documentation.class);
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
        Page<Documentation> documentations = docRepository.findAllByIsPublishTrue(pageable);
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
        documentation.setViews(0);
        documentation.setScore(0d);
        documentation.setIsPublish(false);
        docRepository.insert(documentation);
    }

    @Override
    public List<Documentation> findAll() {
        return docRepository.findAll();
    }

    @Override
    public List<Documentation> findAllByIsPublishTrue() {
        return Optional.ofNullable(docRepository.findAllByIsPublishTrue()).orElse(new ArrayList<>());
    }

    @Override
    public Documentation findDocByDocName(String docName,String username) {
        Documentation documentation = docRepository.findDocumentationByDocNameAndUsername(docName, username);
        if(Objects.isNull(documentation.getTags())){
            documentation.setTags(new HashSet<>());
        }else {
            Tag tag = new Tag();
            tag.setName(documentation.getMainTag());
            documentation.getTags().remove(tag);
        }
        return documentation;
    }

    @Override
    public void saveDocByDocName(Documentation documentation, HttpServletRequest request){
        String[] tags = request.getParameterValues("checkTags");
        if (tags != null) {
            documentation.setTags(Arrays.stream(tags).map(s -> {
                Tag tag = new Tag();
                tag.setName(s);
                return tag;
            }).collect(Collectors.toSet()));
        }else {
            documentation.setTags(new HashSet<>());
        }


        String id = request.getParameter("id");
        documentation.setId(id);
        Tag tag1 = new Tag();
        tag1.setName(documentation.getMainTag());
        documentation.getTags().add(tag1);
        documentation.setModifyTime(DateUtils.nowFormat());

        log.debug("保存文档内容：{}",documentation);

        Query query = new Query(Criteria.where("_id").is(documentation.getId()));
        Update update = new Update();
        mongoTemplate.upsert(query, BlogUtils.createUpdate(update, documentation), Documentation.class);
    }

    @Override
    public List<Documentation> findDocByScoreDesc(Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "score");
        Query query = new Query(Criteria.where("isPublish").is(true));
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
        Query query = new Query(Criteria.where("docName").regex(pattern).and("isPublish").is(true));
        List<Documentation> documentations = mongoTemplate.find(query.with(sort), Documentation.class);
        return Optional.of(documentations).orElse(new ArrayList<>());
    }

    @Override
    public List<Documentation> findDocByRandom(Integer randomCount) {
        try {
            TypedAggregation<Documentation> typedAggr = Aggregation.newAggregation(Documentation.class
                    ,Aggregation.match(Criteria.where("isPublish").is(true))
                    ,Aggregation.sample(randomCount));
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
        Query query = new Query(Criteria.where("isPublish").is(true));
        try {
            return mongoTemplate.find(query.with(sort).limit(limit), Documentation.class);
        }catch (Exception e){
            log.error("博客根据评论数排序查询异常，异常信息：{}",e.getMessage());
            return null;
        }
    }

    @Override
    public List<Documentation> findDocByRecommend(Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "views");
        Query query = new Query(Criteria.where("isPublish").is(true));
        try {
            return mongoTemplate.find(query.with(sort).limit(limit), Documentation.class);
        }catch (Exception e){
            log.error("博客推荐查询异常，异常信息：{}",e.getMessage());
            return null;
        }
    }

    @Override
    public void reviewDoc(String id) {
        BlogReview blogReview = new BlogReview();
        blogReview.setBlogId(id);
        blogReview.setIsPublish(false);
        blogReviewMapper.insert(blogReview);
    }

    @Override
    public void publishDoc(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("isPublish",true);
        mongoTemplate.upsert(query, update, Documentation.class);
    }
}
