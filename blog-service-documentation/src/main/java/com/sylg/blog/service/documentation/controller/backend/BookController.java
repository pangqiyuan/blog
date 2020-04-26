package com.sylg.blog.service.documentation.controller.backend;

import com.sylg.blog.service.documentation.common.constants.Constans;
import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.service.DocumentationService;
import com.sylg.blog.service.documentation.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: blog
 * @description: 后台博客管理
 * @author: 忆地球往事
 * @create: 2020-04-22 19:27
 **/
@Controller
@RequestMapping(value = "/book")
@CrossOrigin
public class BookController {

    @Resource
    private DocumentationService documentationService;

    @Resource
    private TagService tagService;

    /**
    * @Description: 后台博客管理页面
    * @Param: [map, request]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "")
    public String book(ModelMap map,HttpServletRequest request){
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        List<Documentation> documentationList = documentationService.findAllByName(blogUser.getUserName());
        map.addAttribute("bookList",documentationList);
        return "book/index";
    }

    /**
    * @Description: 添加博客
    * @Param: [documentation, request]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @PostMapping(value = "/addDoc")
    public @ResponseBody Result addDoc(Documentation documentation, HttpServletRequest request){
        Result result = new Result();
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        documentation.setUsername(blogUser.getUserName());
        documentationService.insertDoc(documentation);
        result.setCode(BaseResult.SUCCESS);
        result.setMsg("添加成功");
        return result;
    }

    /**
    * @Description: 博客展示
    * @Param: [docName, map, request]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @RequestMapping(value="/{docName}/dashboard",method=RequestMethod.GET)
    public String  dashboard(@PathVariable String docName,ModelMap map,HttpServletRequest request) {
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        Documentation documentation = documentationService.findDocByDocName(docName,blogUser.getUserName());
        map.addAttribute("book",documentation);
        return "book/dashboard";
    }

    /**
    * @Description: 编辑博客
    * @Param: [docName, map, request]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/{docName}/edit")
    public String edit(@PathVariable String docName,ModelMap map,HttpServletRequest request){
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        Documentation documentation = documentationService.findDocByDocName(docName,blogUser.getUserName());
        map.addAttribute("book",documentation);
        map.addAttribute("mainTags",tagService.findByIsMainTag());
        map.addAttribute("tags",tagService.findAllIsNotMainTag());
        return "book/edit";
    }

    /**
    * @Description: 用户管理
    * @Param: [docName, map, request]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/{docName}/users")
    public String users(@PathVariable String docName,ModelMap map,HttpServletRequest request){
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        Documentation documentation = documentationService.findDocByDocName(docName,blogUser.getUserName());
        map.addAttribute("book",documentation);
        return "book/users";
    }

    /**
    * @Description: 博客设置
    * @Param: [docName, map, request]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/{docName}/setting")
    public String setting(@PathVariable String docName,ModelMap map,HttpServletRequest request){
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        Documentation documentation = documentationService.findDocByDocName(docName,blogUser.getUserName());
        map.addAttribute("book",documentation);
        return "book/setting";
    }

    /**
    * @Description: 返回到编辑博客页面
    * @Param: [documentation, request]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @PostMapping(value = "/editDoc")
    public @ResponseBody Result editDoc(Documentation documentation,HttpServletRequest request ){
        documentationService.saveDocByDocName(documentation,request);
        return BaseResult.success("修改成功");
    }

    /**
    * @Description: 根据id删除博客
    * @Param: [id, map, request]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable String id,ModelMap map,HttpServletRequest request){
        documentationService.deleteDocByDocName(id);
        return this.book(map,request);
    }

    /**
    * @Description: 根据id发布博客
    * @Param: [id]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/24
    */
    @GetMapping(value = "/{id}/publish")
    public @ResponseBody Result publish(@PathVariable String id){
        documentationService.reviewDoc(id);
        return BaseResult.success("发布成功");
    }
}
