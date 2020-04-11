package com.sylg.blog.service.documentation.controller.web;

import com.sylg.blog.service.documentation.cache.lock.CacheLock;
import com.sylg.blog.service.documentation.common.constants.Constans;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.service.DocumentationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: blog
 * @description: 博客页面展示
 * @author: 忆地球往事
 * @create: 2020-04-08 21:28
 **/
@Controller
@RequestMapping(value = "/blog")
@Slf4j
public class BlogController {

    @Resource
    private DocumentationService documentationService;

    @GetMapping(value = "/{id}")
    @CacheLock(expired = 30)
    public String dashboard(@PathVariable String id, ModelMap map) {
        log.info("查看博客：{}", id);
        Documentation documentation = documentationService.findDocById(id);
        map.addAttribute("book", documentation);
        return "book/article";
    }


}
