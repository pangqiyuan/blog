package com.sylg.blog.service.documentation.controller.web;

import com.sylg.blog.service.documentation.common.controller.BaseController;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.service.AnnouncementService;
import com.sylg.blog.service.documentation.service.DocumentationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author pangqiyuan
 * @date 2019/12/10
 *
 *  博客主页信息
 */
@Controller
public class IndexController extends BaseController {


    @GetMapping(value = "/")
    public String index(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, ModelMap map)
    {
        map.addAttribute("bookList",documentationService.findAll(pageable));
        sidecar(map);
        return "index";
    }

    @GetMapping(value = "/search")
    public String search(String keyword , ModelMap modelMap){
        if(Objects.isNull(keyword)|| "".equals(keyword)){
            modelMap.addAttribute("searchByKeywordDoc", new ArrayList<>());
        }else {
            modelMap.addAttribute("searchByKeywordDoc", documentationService.searchByKeyword(keyword));
        }
        sidecar(modelMap);
        return "home/search";
    }



}
