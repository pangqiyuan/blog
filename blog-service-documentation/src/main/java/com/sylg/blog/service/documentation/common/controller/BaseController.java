package com.sylg.blog.service.documentation.common.controller;

import com.sylg.blog.service.documentation.service.AnnouncementService;
import com.sylg.blog.service.documentation.service.DocumentationService;
import com.sylg.blog.service.documentation.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;

/**
 * @program: blog
 * @description: base controller
 * @author: 忆地球往事
 * @create: 2020-04-18 18:44
 **/
public class BaseController {

    @Resource
    protected DocumentationService documentationService;

    @Resource
    protected AnnouncementService announcementService;

    @Resource
    protected TagService tagService;


    protected void sidecar(ModelMap map){
        map.addAttribute("popularArticles",documentationService.findDocByScoreDesc(9));
        map.addAttribute("hotArticles",documentationService.findDocByCommentDesc(9));
        map.addAttribute("docByRecommend",documentationService.findDocByRecommend(9));
        map.addAttribute("docByRandom",documentationService.findDocByRandom(1));
        map.addAttribute("mainTags",tagService.findByIsMainTag());
        announcementService.findAll(5).ifPresent(announcements -> map.addAttribute("announcements",announcements));
    }
}
