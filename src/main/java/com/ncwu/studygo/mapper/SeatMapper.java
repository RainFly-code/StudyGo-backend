package com.ncwu.studygo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncwu.studygo.entity.Seat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 座位Mapper接口
 */
@Mapper
public interface SeatMapper extends BaseMapper<Seat> {
    // 继承BaseMapper后，已经有基本的CRUD方法
    // 如需自定义复杂查询，可在此添加方法
}