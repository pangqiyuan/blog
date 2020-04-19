package com.sylg.blog.service.documentation.interceptor;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.common.utils.BlogUtils;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.repository.DocRepository;
import com.sylg.blog.service.documentation.service.DocumentationService;
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

    @After("execution(* com.sylg.blog.service.documentation.controller.web.BlogController.dashboard(..))")
    public void computeScore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        ModelMap modelMap = (ModelMap) args[1];
        Documentation documentation = (Documentation) modelMap.get("book");
        Double score = BlogUtils.blogScore(documentation.getViews(), documentation.getCreateTime());
        log.debug("计算分数后的blog{},score:{}",documentation.getId(),score);
        Query query = Query.query(Criteria.where("_id").is(documentation.getId()));
        Update update = new Update();
        update.set("score",score);
        mongoTemplate.updateFirst(query,update, Documentation.class);
    }

    @Around("execution(* com.sylg.blog.service.documentation.controller.web.BlogController.comment(..))")
    public Result computeCommentNumber(ProceedingJoinPoint joinPoint) {
        try {
            String code = ((Result) joinPoint.proceed()).getCode();
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
