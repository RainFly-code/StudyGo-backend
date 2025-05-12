package com.ncwu.studygo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
@TableName("user")
public class User {
    
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 角色 0:普通用户 1:管理员
     */
    private Integer role;
    
    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField("updateTime")
    private LocalDateTime updateTime;
    
    /**
     * 性别：0-未知 1-男 2-女
     */
    private Integer gender;
    
    /**
     * 年龄
     */
    private Integer age;
}