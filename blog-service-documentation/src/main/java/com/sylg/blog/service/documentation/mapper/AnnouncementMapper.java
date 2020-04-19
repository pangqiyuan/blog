package com.sylg.blog.service.documentation.mapper;

import com.sylg.blog.service.documentation.common.mapper.MyMapper;
import com.sylg.blog.service.documentation.domain.Announcement;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AnnouncementMapper extends  MyMapper<Announcement> {
    int insertOrUpdate(Announcement record);

    int insertOrUpdateSelective(Announcement record);

    List<Announcement> selectAllByAnnouncementTimeDesc(Integer limit);
}