package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.Announcement;

import java.util.List;
import java.util.Optional;

/**
 * 公告 service
 * @author 忆地球往事
 */
public interface AnnouncementService{


    int insertOrUpdate(Announcement record);

    int insertOrUpdateSelective(Announcement record);

    void insertAnnouncement(Announcement announcement);

    /**
     * 查询公告
     * @param limit 数量
     * @return announcements
     */
    Optional<List<Announcement>> findAll(Integer limit);

    /**
     * 查询公告
     *
     * @return announcements
     */
    List<Announcement> findAll();


    /**
     *  删除公告
     * @param id 公告id
     */
    void deleteAnnouncement(Integer id);

}
