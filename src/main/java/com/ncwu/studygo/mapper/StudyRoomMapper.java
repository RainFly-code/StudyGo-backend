package com.ncwu.studygo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncwu.studygo.entity.StudyRoom;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自习室Mapper接口
 */
@Mapper
public interface StudyRoomMapper extends BaseMapper<StudyRoom> {
}