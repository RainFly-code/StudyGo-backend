package com.ncwu.studygo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 座位实体类
 */
@Data
@TableName("seat")
public class Seat {
    
    /**
     * 座位ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 自习室ID
     */
    private Long roomId;
    
    /**
     * 座位编号
     */
    private String seatNumber;
    
    /**
     * 状态 0:可用 1:已预约 2:维护中
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