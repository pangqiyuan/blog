package com.sylg.blog.service.documentation.controller;

import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.service.DocumentationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping(value = "/index")
    public String index(ModelMap map, HttpServletRequest request)
    {
        List<Documentation> documentations = documentationService.findAll();
        map.addAttribute("bookList",documentations);
        return "index";
    }

    @GetMapping(value = "/index1")
    public String index()
    {
        return "index1";
    }

}
