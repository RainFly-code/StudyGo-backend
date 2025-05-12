package com.ncwu.studygo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 自习室实体类
 */
@Data
@TableName("study_room")
public class StudyRoom {
    
    /**
     * 自习室ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 自习室名称
     */
    private String name;
    
    /**
     * 容量
     */
    private Integer capacity;
    
    /**
     * 状态 0:可用 1:维护中
     */
    private Integer status;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 开放时间
     */
    private LocalTime openTime;
    
    /**
     * 关闭时间
     */
    private LocalTime closeTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}