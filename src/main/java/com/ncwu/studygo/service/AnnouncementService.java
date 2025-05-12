package com.ncwu.studygo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncwu.studygo.entity.Announcement;

/**
 * 公告服务接口
 */
public interface AnnouncementService extends IService<Announcement> {

    /**
     * 获取公告列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return 公告分页列表
     */
    IPage<Announcement> getAnnouncementList(Integer page, Integer size);

    /**
     * 获取公告详情
     *
     * @param id 公告ID
     * @return 公告信息
     */
    Announcement getAnnouncementDetail(Long id);

    /**
     * 发布公告
     *
     * @param announcement 公告信息
     * @return 是否成功
     */
    boolean addAnnouncement(Announcement announcement);

    /**
     * 更新公告
     *
     * @param announcement 公告信息
     * @return 是否成功
     */
    boolean updateAnnouncement(Announcement announcement);

    /**
     * 删除公告
     *
     * @param id 公告ID
     * @return 是否成功
     */
    boolean deleteAnnouncement(Long id);

    /**
     * 更新公告状态
     *
     * @param id     公告ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateAnnouncementStatus(Long id, Integer status);
} 