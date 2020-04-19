package com.sylg.blog.service.documentation.service;

import com.sylg.blog.service.documentation.domain.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService{


    int insertOrUpdate(Announcement record);

    int insertOrUpdateSelective(Announcement record);

    void insertAnnouncement(Announcement announcement);

    Optional<List<Announcement>> findAll(Integer limit);

}
