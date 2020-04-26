package com.sylg.blog.service.documentation.controller.web;

import com.sylg.blog.service.documentation.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: blog
 * @description: tag controller
 * @author: 忆地球往事
 * @create: 2020-04-18 19:09
 **/
@Controller
public class TagController extends BaseController {


    /**
    * @Description: 返回所有标签并排序
    * @Param: [map]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/tags")
    public String tag(ModelMap map){
        map.addAttribute("tags",  tagService.findAllBySort());
        sidecar(map);
        return "tags";
    }


    //@PostMapping(value = "/insertTag")
    //public String tag(Tag tag){
    //    tagService.insertTag(tag);
    //    return "tags";
    //}

}
