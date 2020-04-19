package com.sylg.blog.service.documentation.controller.web;

import com.sylg.blog.service.documentation.domain.Tag;
import com.sylg.blog.service.documentation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @program: blog
 * @description: tag controller
 * @author: 忆地球往事
 * @create: 2020-04-18 19:09
 **/
@Controller
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/tags")
    public String tag(ModelMap map){
        map.addAttribute("tags",  tagService.findAll());
        return "tags";
    }


    //@PostMapping(value = "/insertTag")
    //public String tag(Tag tag){
    //    tagService.insertTag(tag);
    //    return "tags";
    //}

}
