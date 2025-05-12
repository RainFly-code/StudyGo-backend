package com.ncwu.studygo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 预约实体类
 */
@Data
@TableName("reservation")
public class Reservation {
    
    /**
     * 预约ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 座位ID
     */
    private Long seatId;
    
    /**
     * 自习室ID
     */
    private Long roomId;
    
    /**
     * 座位编号
     */
    private String seatNumber;
    
    /**
     * 自习室名称
     */
    private String roomName;
    
    /**
     * 预约开始日期
     */
    private LocalDate date;
    
    /**
     * 预约天数
     */
    private Integer days;
    
    /**
     * 状态 0:待审核 1:已通过 2:已拒绝 3:已取消
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}