package com.ncwu.studygo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncwu.studygo.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * 反馈Mapper接口
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
} 