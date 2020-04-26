package com.sylg.blog.service.documentation.controller.backend;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.domain.Tag;
import com.sylg.blog.service.documentation.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @program: blog
 * @description: 后台标签管理
 * @author: 忆地球往事
 * @create: 2020-04-22 19:27
 **/
@Controller
@RequestMapping("/backTag")
public class BackTagController {

    private final TagService tagService;

    public BackTagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
    * @Description: 返回所有标签
    * @Param: [map]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping("/tags")
    public String tags(ModelMap map){
        map.addAttribute("tagList",tagService.findAll());
        return "/book/tags";
    }

    /**
    * @Description: 新增标签
    * @Param: [tag]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @PostMapping("/insertTag")
    public @ResponseBody Result insertTag(Tag tag){
        tagService.insertTag(tag);
        return BaseResult.success("添加成功");
    }

    /**
    * @Description: 删除标签
    * @Param: [tid 标签id]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @PostMapping("/deleteTag")
    public @ResponseBody Result deleteTag(String tid){
        tagService.delete(tid);
        return BaseResult.success("删除成功");
    }
}
