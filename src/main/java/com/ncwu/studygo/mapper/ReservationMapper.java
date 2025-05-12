package com.ncwu.studygo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ncwu.studygo.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

/**
 * 预约Mapper接口
 */
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
    
    /**
     * 检查预约日期是否重叠
     * 
     * @param seatId 座位ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 重叠的预约数量
     */
    int checkDateOverlap(@Param("seatId") Long seatId, 
                         @Param("startDate") LocalDate startDate, 
                         @Param("endDate") LocalDate endDate);
} 