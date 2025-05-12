package com.ncwu.studygo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncwu.studygo.entity.Feedback;

/**
 * 反馈服务接口
 */
public interface FeedbackService extends IService<Feedback> {

    /**
     * 提交反馈
     *
     * @param feedback 反馈信息
     * @return 反馈ID
     */
    Long addFeedback(Feedback feedback);

    /**
     * 获取用户反馈列表
     *
     * @param userId 用户ID
     * @param status 状态（可选）
     * @param page   页码
     * @param size   每页数量
     * @return 反馈分页列表
     */
    IPage<Feedback> getUserFeedbacks(Long userId, Integer status, Integer page, Integer size);

    /**
     * 获取反馈详情
     *
     * @param id 反馈ID
     * @return 反馈信息
     */
    Feedback getFeedbackDetail(Long id);

    /**
     * 管理员获取反馈列表
     *
     * @param type   反馈类型（可选）
     * @param status 状态（可选）
     * @param page   页码
     * @param size   每页数量
     * @return 反馈分页列表
     */
    IPage<Feedback> getFeedbackList(Integer type, Integer status, Integer page, Integer size);

    /**
     * 管理员处理反馈
     *
     * @param id     反馈ID
     * @param status 状态
     * @param reply  回复内容
     * @return 是否成功
     */
    boolean handleFeedback(Long id, Integer status, String reply);
} 