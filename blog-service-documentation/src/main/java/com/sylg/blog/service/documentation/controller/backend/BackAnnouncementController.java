package com.sylg.blog.service.documentation.controller.backend;

import com.sylg.blog.service.documentation.common.dto.BaseResult;
import com.sylg.blog.service.documentation.common.dto.Result;
import com.sylg.blog.service.documentation.domain.Announcement;
import com.sylg.blog.service.documentation.service.AnnouncementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: blog
 * @description: 后台公告管理
 * @author: 忆地球往事
 * @create: 2020-04-26 12:42
 **/
@Controller
@RequestMapping("/announcement")
public class BackAnnouncementController {

    private AnnouncementService announcementService;

    public BackAnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    /**
    * @Description: 查询所有公告
    * @Param: [modelMap]
    * @return: java.lang.String
    * @Author: 忆地球往事
    * @Date: 2020/4/26
    */
    @GetMapping("/findAnnouncement")
    public String findAnnouncement(ModelMap modelMap){
        modelMap.addAttribute("announcementList",announcementService.findAll());
        return "/book/announcements";
    }

    /**
    * @Description: 删除标签
    * @Param: [id]
    * @return: com.sylg.blog.service.documentation.common.dto.Result
    * @Author: 忆地球往事
    * @Date: 2020/4/26
    */
    @PostMapping("/deleteAnnouncement")
    public @ResponseBody Result deleteAnnouncement(Integer id){
        announcementService.deleteAnnouncement(id);
        return BaseResult.success("删除成功");
    }

    @PostMapping("/insertAnnouncement")
    public @ResponseBody Result insertAnnouncement(Announcement announcement){
        announcementService.insertAnnouncement(announcement);
        return BaseResult.success("删除成功");
    }
}
