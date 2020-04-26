package com.sylg.blog.service.documentation.mapper;

import com.sylg.blog.service.documentation.common.mapper.MyMapper;
import com.sylg.blog.service.documentation.domain.Announcement;

import java.util.List;

/**
 * 公告mapper
 * @author 忆地球往事
 */
public interface AnnouncementMapper extends  MyMapper<Announcement> {
    int insertOrUpdate(Announcement record);

    int insertOrUpdateSelective(Announcement record);

    List<Announcement> selectAllByAnnouncementTimeDesc(Integer limit);

    int deleteById(Integer id);
}