package com.sylg.blog.service.documentation.controller.web;

import com.sylg.blog.service.documentation.cache.lock.CacheLock;
import com.sylg.blog.service.documentation.common.controller.BaseController;
import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.domain.Documentation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @program: blog
 * @description: 博客页面展示
 * @author: 忆地球往事
 * @create: 2020-04-08 21:28
 **/
@Controller
@RequestMapping(value = "/blog")
@Slf4j
public class BlogController extends BaseController {


    /**
    * @Description: 根据id展示博客
    * @Param: [id, map]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/{id}")
    @CacheLock(expired = 30)
    public String dashboard(@PathVariable String id, ModelMap map) {
        log.info("查看博客：{}", id);
        Documentation documentation = documentationService.findDocById(id);
        map.addAttribute("book", documentation);
        map.addAttribute("bookByMT",  documentationService.findDocByIdsAndViewsSort(tagService.findBlogIdByMainTag(documentation.getMainTag()),8));
        sidecar(map);
        return "book/article";
    }

    /**
    * @Description: 添加博客评论
    * @Param: [id, commentByBean]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @PostMapping(value = "/comment")
    public @ResponseBody Result comment(String id ,Documentation.CommentByBean commentByBean){
        log.debug("保存博客:{}的评论",id);
        if (documentationService.saveCommentByDocId(id ,commentByBean)) {
            return BaseResult.success("评论保存成功");
        }
        return BaseResult.error("评论保存失败");
    }

    /**
    * @Description: 添加博客评论回复
    * @Param: [id, replyComment, replyCid]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @PostMapping(value = "/replyComment")
    public @ResponseBody Result replyComment(String id ,Documentation.ReplyComment replyComment ,String replyCid){
            log.debug("保存博客{}的评论{}的回复内容{}",id ,replyCid,replyComment);
            documentationService.saveCommentByDocId(id,replyComment,replyCid);
            return BaseResult.success("评论保存成功");

    }

    /**
    * @Description: 根据主标签推荐博客
    * @Param: [mainTag, map]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/docByTag/{mainTag}")
    public String findByTag(@PathVariable String mainTag,ModelMap map){
        List<String> blogIds = tagService.findBlogIdByMainTag(mainTag);
        map.addAttribute("tagName",mainTag);
        map.addAttribute("tagDocs",documentationService.findDocByIds(blogIds));
        sidecar(map);
        return "/home/category";
    }

}
