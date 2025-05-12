package com.ncwu.studygo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncwu.studygo.entity.Announcement;
import com.ncwu.studygo.mapper.AnnouncementMapper;
import com.ncwu.studygo.service.AnnouncementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

/**
 * 公告服务实现类
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Override
    public IPage<Announcement> getAnnouncementList(Integer page, Integer size) {
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        // 只查询已发布的公告
        queryWrapper.eq(Announcement::getStatus, 1)
                    .orderByDesc(Announcement::getPublisherId);
        
        return baseMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    @Override
    public Announcement getAnnouncementDetail(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAnnouncement(Announcement announcement) {
        announcement.setPublishTime(LocalDateTime.now());
        
        return baseMapper.insert(announcement) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAnnouncement(@RequestBody Announcement announcement) {
        Announcement existAnnouncement = baseMapper.selectById(announcement.getId());
        if (existAnnouncement == null) {
            throw new RuntimeException("公告不存在");
        }
        
        announcement.setUpdateTime(LocalDateTime.now());
        
        return baseMapper.updateById(announcement) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAnnouncement(Long id) {
        Announcement announcement = baseMapper.selectById(id);
        if (announcement == null) {
            throw new RuntimeException("公告不存在");
        }
        
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAnnouncementStatus(Long id, Integer status) {
        Announcement announcement = baseMapper.selectById(id);
        if (announcement == null) {
            throw new RuntimeException("公告不存在");
        }
        
        announcement.setStatus(status);
        announcement.setUpdateTime(LocalDateTime.now());
        
        return baseMapper.updateById(announcement) > 0;
    }
} 