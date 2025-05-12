package com.ncwu.studygo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ncwu.studygo.common.result.Result;
import com.ncwu.studygo.entity.Feedback;
import com.ncwu.studygo.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 反馈管理控制器
 */
@RestController
@RequestMapping("/api")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 提交反馈
     */
    @PostMapping("/feedback/add")
    public Result<Map<String, Object>> addFeedback(
            Feedback feedback,
            HttpSession session) {
        // 从session中获取用户ID
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.unauthorized();
        }

        // 设置用户ID
        feedback.setUserId(userId);

        // 提交反馈
        Long feedbackId = feedbackService.addFeedback(feedback);

        // 返回反馈ID
        Map<String, Object> result = new HashMap<>();
        result.put("id", feedbackId);
        return Result.success(result, "反馈提交成功");
    }

    /**
     * 获取个人反馈列表
     */
    @GetMapping("/feedback/list")
    public Result<IPage<Feedback>> getUserFeedbacks(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status,
            HttpSession session) {
        // 从session中获取用户ID
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.unauthorized();
        }

        // 获取反馈列表
        IPage<Feedback> feedbackList = feedbackService.getUserFeedbacks(userId, status, page, size);
        return Result.success(feedbackList);
    }

    /**
     * 获取反馈详情
     */
    @GetMapping("/feedback/{id}")
    public Result<Feedback> getFeedbackDetail(
            @PathVariable Long id,
            HttpSession session) {
        // 从session中获取用户ID
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.unauthorized();
        }

        // 获取反馈详情
        Feedback feedback = feedbackService.getFeedbackDetail(id);
        
        // 判断是否为当前用户的反馈
        if (feedback != null && !feedback.getUserId().equals(userId) && !isAdmin(session)) {
            return Result.forbidden();
        }

        return Result.success(feedback);
    }

    /**
     * 管理员获取反馈列表
     */
    @GetMapping("/admin/feedback/list")
    public Result<IPage<Feedback>> getFeedbackList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 获取反馈列表
        IPage<Feedback> feedbackList = feedbackService.getFeedbackList(type, status, page, size);
        return Result.success(feedbackList);
    }

    /**
     * 管理员处理反馈
     */
    @PutMapping("/admin/feedback/{id}")
    public Result<Boolean> handleFeedback(
            @PathVariable Long id,
            @RequestParam Integer status,
            @RequestParam(required = false) String reply,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 处理反馈
        boolean result = feedbackService.handleFeedback(id, status, reply);
        
        String message;
        switch (status) {
            case 1:
                message = "反馈已标记为处理中";
                break;
            case 2:
                message = "反馈已处理完成";
                break;
            default:
                message = "反馈状态已更新";
        }
        
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