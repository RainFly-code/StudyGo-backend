package com.ncwu.studygo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncwu.studygo.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公告Mapper接口
 */
@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {
} 