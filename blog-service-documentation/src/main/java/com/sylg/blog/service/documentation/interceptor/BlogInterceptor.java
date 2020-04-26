package com.sylg.blog.service.documentation.interceptor;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.common.utils.BlogUtils;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.domain.Tag;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;


/**
 * @program: blog
 * @description: blog Interceptor
 * @author: 忆地球往事
 * @create: 2020-04-16 20:07
 **/
@Slf4j
@Aspect
@Configuration
public class BlogInterceptor {

    @Resource
    private MongoTemplate mongoTemplate;


    /**
    * @Description: 在每次访问时计算博客分数
    * @Param: [joinPoint]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @After("execution(* com.sylg.blog.service.documentation.controller.web.BlogController.dashboard(..))")
    public void computeScore(JoinPoint joinPoint) {
        //获得参数modelMap
        Object[] args = joinPoint.getArgs();
        ModelMap modelMap = (ModelMap) args[1];
        Documentation documentation = (Documentation) modelMap.get("book");

        //计算分数
        Double score = BlogUtils.blogScore(documentation.getViews(), documentation.getCreateTime());

        log.debug("计算分数后的blog{},score:{}",documentation.getId(),score);

        //更新数据库
        Query query = Query.query(Criteria.where("_id").is(documentation.getId()));
        Update update = new Update();
        update.set("score",score);
        mongoTemplate.updateFirst(query,update, Documentation.class);
    }

    /**
    * @Description: 处理标签在保存博客时
    * @Param: [joinPoint]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @Around("execution(* com.sylg.blog.service.documentation.service.DocumentationService.saveDocByDocName(..))")
    public void dealTags(ProceedingJoinPoint joinPoint) {
        try {
            //根据标签移除对应的博客
            beforeDealTags(joinPoint);
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        //根据标签添加对应的博客
        afterDealTags(joinPoint);
    }

    private void beforeDealTags(ProceedingJoinPoint joinPoint) {
        //根据id查询博客
        Object[] args = joinPoint.getArgs();
        String id = ((HttpServletRequest) args[1]).getParameter("id");
        Query query1 = new Query(Criteria.where("_id").is(id));
        Documentation documentation = mongoTemplate.findOne(query1, Documentation.class);

        assert documentation != null;
        Set<Tag> tags = documentation.getTags();

        //遍历删除标签对应的博客数量和id
        if (tags != null) {
            tags.forEach(tag -> {
                log.debug("博客删除标签{}",tag.getName());
                Query query = new Query(Criteria.where("name").is(tag.getName()));
                Update update = new Update();
                update.inc("blogNumber" ,-1);
                update.pull("blogId",documentation.getId());
                mongoTemplate.updateFirst(query,update,Tag.class);
            });
        }
    }

    private void afterDealTags(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Documentation documentation = (Documentation) args[0];
        Set<Tag> tags = documentation.getTags();
        //遍历增加标签对应的博客数量和id
        tags.forEach(tag -> {
            log.debug("更新标签{}",tag.getName());
            Query query = new Query(Criteria.where("name").is(tag.getName()));
            Update update = new Update();
            update.inc("blogNumber");
            update.addToSet("blogId",documentation.getId());
            mongoTemplate.updateFirst(query,update,Tag.class);
        });
    }

    /**
    * @Description: 增加评论数
    * @Param: [joinPoint]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @Around("execution(* com.sylg.blog.service.documentation.controller.web.BlogController.comment(..))")
    public Result computeCommentNumber(ProceedingJoinPoint joinPoint) {
        try {
            String code = ((Result) joinPoint.proceed()).getCode();

            //若评论添加成功，添加评论数
            if(code.equals(BaseResult.SUCCESS)){
                Object[] args = joinPoint.getArgs();
                String blogId = (String) args[0];
                log.debug("增加blog{}的评论总数",blogId);
                Query query = Query.query(Criteria.where("_id").is(blogId));
                Update update = new Update();
                update.inc("commentSize");
                mongoTemplate.updateFirst(query,update, Documentation.class);
            }
            return BaseResult.success("评论保存成功");
        } catch (Throwable throwable) {
            log.error("博客零评论数量增加异常：{}",throwable.getMessage());
            return BaseResult.error("评论保存失败");
        }

    }
}
