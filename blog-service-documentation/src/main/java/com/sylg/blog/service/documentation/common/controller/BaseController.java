package com.sylg.blog.service.documentation.common.controller;

import com.sylg.blog.service.documentation.service.AnnouncementService;
import com.sylg.blog.service.documentation.service.DocumentationService;
import com.sylg.blog.service.documentation.service.TagService;
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

    /**
    * @Description: 用于blog的周边数据展示
    * @Param: [map]
    * @return: void
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    protected void sidecar(ModelMap map){
        //热门文章
        map.addAttribute("popularArticles",documentationService.findDocByScoreDesc(9));
        //热评文章
        map.addAttribute("hotArticles",documentationService.findDocByCommentDesc(9));
        //推荐文章
        map.addAttribute("docByRecommend",documentationService.findDocByRecommend(9));
        //随机文章
        map.addAttribute("docByRandom",documentationService.findDocByRandom(1));
        //页面主标签
        map.addAttribute("mainTags",tagService.findByIsMainTag());
        //公告
        announcementService.findAll(5).ifPresent(announcements -> map.addAttribute("announcements",announcements));
    }
}
