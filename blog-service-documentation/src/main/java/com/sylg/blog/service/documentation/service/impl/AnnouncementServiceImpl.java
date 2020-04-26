package com.sylg.blog.service.documentation.service.impl;

import com.sylg.blog.service.documentation.common.utils.DateUtils;
import com.sylg.blog.service.documentation.domain.Announcement;
import com.sylg.blog.service.documentation.mapper.AnnouncementMapper;
import com.sylg.blog.service.documentation.service.AnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * announcement service impl
 * @author 忆地球往事
 */
@Service
@Slf4j
public class AnnouncementServiceImpl implements AnnouncementService{

    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public int insertOrUpdate(Announcement record) {
        return announcementMapper.insertOrUpdate(record);
    }


    @Override
    public void insertAnnouncement(Announcement announcement) {
        try {
            announcement.setAnnouncementTime(DateUtils.nowFormat());
            announcementMapper.insert(announcement);
        } catch (Exception e) {
            log.error("公告添加异常，异常信息：{}" ,e.getMessage());
        }
    }

    @Override
    public Optional<List<Announcement>> findAll(Integer limit) {
        try {
            return Optional.ofNullable(announcementMapper.selectAllByAnnouncementTimeDesc(limit));
        }catch (Exception e){
            log.error("公告查询异常，异常信息：{}" ,e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Announcement> findAll() {
        return Optional.ofNullable(announcementMapper.selectAll()).orElse(new ArrayList<>());
    }

    @Override
    public void deleteAnnouncement(Integer id) {
        int i = announcementMapper.deleteById(id);
        if(i > 0){
            log.debug("删除公告{}",id);
        }
    }

    @Override
    public int insertOrUpdateSelective(Announcement record) {
        return announcementMapper.insertOrUpdateSelective(record);
    }

}
