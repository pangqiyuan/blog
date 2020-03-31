package com.sylg.blog.service.documentation.controller;

import com.sylg.blog.service.documentation.common.constants.Constans;
import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.utils.Result;
import com.sylg.blog.service.documentation.domain.BlogUser;
import com.sylg.blog.service.documentation.domain.Documentation;
import com.sylg.blog.service.documentation.service.DocumentationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "book")
@CrossOrigin
public class BookController {

    @Resource
    private DocumentationService documentationService;



    @GetMapping(value = "")
    public String book(ModelMap map,HttpServletRequest request){
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        List<Documentation> documentationList = documentationService.findAllByName(blogUser.getUserName());
        map.addAttribute("bookList",documentationList);
        return "book/index";
    }

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

    @RequestMapping(value="/{docName}/dashboard",method=RequestMethod.GET)
    public String  dashboard(@PathVariable String docName,ModelMap map,HttpServletRequest request) {
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        Documentation documentation = documentationService.findDocByDocName(docName,blogUser.getUserName());
        map.addAttribute("book",documentation);
        return "book/dashboard";
    }

    @GetMapping(value = "/{docName}/edit")
    public String edit(@PathVariable String docName,ModelMap map,HttpServletRequest request){
        BlogUser blogUser = (BlogUser)request.getSession().getAttribute(Constans.CURRENT_USER);
        Documentation documentation = documentationService.findDocByDocName(docName,blogUser.getUserName());
        map.addAttribute("book",documentation);
        return "book/edit";

    }

    @PostMapping(value = "/editDoc")
    public @ResponseBody Result editDoc(Documentation documentation,HttpServletRequest request){
        String id = request.getParameter("_id");
        documentation.set_id(id);
        Result result = new Result();
        documentationService.saveDocByDocName(documentation);
        result.setCode(BaseResult.SUCCESS);
        result.setMsg("修改成功");
        return result;
    }

    @GetMapping(value = "/{id}/delete")
    public String delete(@PathVariable String id,ModelMap map,HttpServletRequest request){
        documentationService.deleteDocByDocName(id);
        return this.book(map,request);
    }
}
