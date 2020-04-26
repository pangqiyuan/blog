package com.sylg.blog.service.documentation.controller.backend;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.domain.BlogReview;
import com.sylg.blog.service.documentation.service.BlogReviewService;
import com.sylg.blog.service.documentation.service.DocumentationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: blog
 * @description: manager controller
 * @author: 忆地球往事
 * @create: 2020-04-26 15:55
 **/
@Controller
@RequestMapping("/manager")
public class ManagerController {

    private DocumentationService documentationService;

    private BlogReviewService blogReviewService;

    public ManagerController(DocumentationService documentationService, BlogReviewService blogReviewService) {
        this.documentationService = documentationService;
        this.blogReviewService = blogReviewService;
    }

    @GetMapping("/")
    public String managerIndex(ModelMap modelMap){
        modelMap.addAttribute("bookList",documentationService.findAll());
        return "/home/index";
    }

    @GetMapping("/reviewSuccess")
    public String reviewSuccess(ModelMap modelMap){
        modelMap.addAttribute("bookList",documentationService.findAllByIsPublishTrue());
        return "/home/reviewSuccess";
    }

    @GetMapping("/reviewing")
    public String reviewing(ModelMap modelMap){
        modelMap.addAttribute("bookList",documentationService.findDocByIds(blogReviewService.findAllByIsPublishFalse()));
        return "/home/reviewing";
    }

    @PostMapping("/reviewBlog")
    public @ResponseBody Result reviewBlog(String id ,Boolean isReview){
        if(isReview){
            documentationService.publishDoc(id);
            BlogReview blogReview = new BlogReview();
            blogReview.setIsPublish(true);
            blogReview.setBlogId(id);
            blogReviewService.publishBlogByBlogId(blogReview);
        }else {
            blogReviewService.delete(id);
        }
        return BaseResult.success("审核确认成功");
    }
}
