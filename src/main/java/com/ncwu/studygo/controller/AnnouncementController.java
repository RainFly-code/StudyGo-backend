package com.ncwu.studygo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ncwu.studygo.common.result.Result;
import com.ncwu.studygo.entity.Announcement;
import com.ncwu.studygo.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 公告管理控制器
 */
@RestController
@RequestMapping("/api")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * 获取公告列表
     */
    @GetMapping("/announcement/list")
    public Result<IPage<Announcement>> getAnnouncementList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        // 获取公告列表
        IPage<Announcement> announcementList = announcementService.getAnnouncementList(page, size);
        return Result.success(announcementList);
    }

    /**
     * 获取公告详情
     */
    @GetMapping("/announcement/{id}")
    public Result<Announcement> getAnnouncementDetail(@PathVariable Long id) {
        // 获取公告详情
        Announcement announcement = announcementService.getAnnouncementDetail(id);
        return Result.success(announcement);
    }

    /**
     * 发布公告
     */
    @PostMapping("/admin/announcement/add")
    public Result<Boolean> addAnnouncement(
            @RequestBody Announcement announcement,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 设置发布者ID
        Long userId = (Long) session.getAttribute("userId");
        announcement.setPublisherId(userId);

        // 发布公告
        boolean result = announcementService.addAnnouncement(announcement);
        return Result.success(result, "公告发布成功");
    }

    /**
     * 更新公告
     */
    @PutMapping("/admin/announcement/{id}")
    public Result<Boolean> updateAnnouncement(
            @PathVariable Long id,
            @RequestBody  Announcement announcement,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 设置ID
        announcement.setId(id);

        // 更新公告
        boolean result = announcementService.updateAnnouncement(announcement);
        return Result.success(result, "公告更新成功");
    }

    /**
     * 删除公告
     */
    @DeleteMapping("/admin/announcement/{id}")
    public Result<Boolean> deleteAnnouncement(
            @PathVariable Long id,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 删除公告
        boolean result = announcementService.deleteAnnouncement(id);
        return Result.success(result, "公告删除成功");
    }

    /**
     * 更新公告状态
     */
    @PutMapping("/admin/announcement/{id}/status")
    public Result<Boolean> updateAnnouncementStatus(
            @PathVariable Long id,
            @RequestParam Integer status,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 更新公告状态
        boolean result = announcementService.updateAnnouncementStatus(id, status);
        
        String message = status == 1 ? "公告已发布" : "公告已下架";
        return Result.success(result, message);
    }
    
    /**
     * 判断当前用户是否为管理员
     * @param session HTTP会话
     * @return 是否为管理员
     */
    private boolean isAdmin(HttpSession session) {
        Integer role = (Integer) session.getAttribute("role");
        return role != null && role == 1; // 角色=1为管理员
    }
} 