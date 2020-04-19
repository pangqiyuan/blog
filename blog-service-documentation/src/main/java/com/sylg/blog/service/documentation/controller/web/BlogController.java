package com.sylg.blog.service.documentation.controller.web;

import com.sylg.blog.service.documentation.cache.lock.CacheLock;
import com.sylg.blog.service.documentation.common.constants.Constans;
import com.sylg.blog.service.documentation.common.controller.BaseController;
import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.service.DocumentationService;
import com.sylg.blog.service.documentation.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

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



    @GetMapping(value = "/{id}")
    @CacheLock(expired = 30)
    public String dashboard(@PathVariable String id, ModelMap map) {
        log.info("查看博客：{}", id);
        Documentation documentation = documentationService.findDocById(id);
        map.addAttribute("book", documentation);
        sidecar(map);
        return "book/article";
    }

    @PostMapping(value = "/comment")
    public @ResponseBody Result comment(String id ,Documentation.CommentByBean commentByBean){
        log.debug("保存博客:{}的评论",id);
        if (documentationService.saveCommentByDocId(id ,commentByBean)) {
            return BaseResult.success("评论保存成功");
        }
        return BaseResult.error("评论保存失败");
    }

    @PostMapping(value = "/replyComment")
    public @ResponseBody Result replyComment(String id ,Documentation.ReplyComment replyComment ,String replyCid){
            log.debug("保存博客{}的评论{}的回复内容{}",id ,replyCid,replyComment);
            documentationService.saveCommentByDocId(id,replyComment,replyCid);
            return BaseResult.success("评论保存成功");

    }

    @GetMapping(value = "/docByTag/{mainTag}")
    public String findByTag(@PathVariable String mainTag,ModelMap map){
        List<String> blogIds = tagService.findBlogIdByMainTag(mainTag);
        map.addAttribute("tagName",mainTag);
        map.addAttribute("tagDocs",documentationService.findDocByIds(blogIds));
        sidecar(map);
        return "/home/category";
    }

}
