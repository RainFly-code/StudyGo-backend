package com.ncwu.studygo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncwu.studygo.entity.Feedback;
import com.ncwu.studygo.mapper.FeedbackMapper;
import com.ncwu.studygo.service.FeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 反馈服务实现类
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addFeedback(Feedback feedback) {
        feedback.setStatus(0); // 设置状态为待处理
        feedback.setCreateTime(LocalDateTime.now());
        
        baseMapper.insert(feedback);
        return feedback.getId();
    }

    @Override
    public IPage<Feedback> getUserFeedbacks(Long userId, Integer status, Integer page, Integer size) {
        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Feedback::getUserId, userId);
        
        if (status != null) {
            queryWrapper.eq(Feedback::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Feedback::getCreateTime);
        
        return baseMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    @Override
    public Feedback getFeedbackDetail(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public IPage<Feedback> getFeedbackList(Integer type, Integer status, Integer page, Integer size) {
        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();
        
        if (type != null) {
            queryWrapper.eq(Feedback::getType, type);
        }
        
        if (status != null) {
            queryWrapper.eq(Feedback::getStatus, status);
        }
        
        queryWrapper.orderByDesc(Feedback::getCreateTime);
        
        return baseMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleFeedback(Long id, Integer status, String reply) {
        Feedback feedback = baseMapper.selectById(id);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        
        feedback.setStatus(status);
        feedback.setReply(reply);
        feedback.setUpdateTime(LocalDateTime.now());
        
        return baseMapper.updateById(feedback) > 0;
    }
} 