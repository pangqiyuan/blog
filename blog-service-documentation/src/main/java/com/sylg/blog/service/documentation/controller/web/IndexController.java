package com.sylg.blog.service.documentation.controller.web;

import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.service.DocumentationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

import java.util.List;


/**
 *
 * @author pangqiyuan
 * @date 2019/12/10
 *
 *  博客主页信息
 */
@Controller
public class IndexController {

    @Resource
    private DocumentationService documentationService;

    @GetMapping(value = "/")
    public String index(@PageableDefault(size = 8, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, ModelMap map)
    {
        List<Documentation> documentations = documentationService.findAll(pageable);
        map.addAttribute("bookList",documentations);
        return "index";
    }

    @GetMapping(value = "/index1")
    public String index()
    {
        return "index1";
    }

}
