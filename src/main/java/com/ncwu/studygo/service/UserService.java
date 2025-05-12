package com.ncwu.studygo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncwu.studygo.entity.User;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    boolean register(User user);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户信息，失败返回null
     */
    User login(String username, String password);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getUserInfo(Long id);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新结果
     */
    boolean updateUserInfo(User user);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    boolean updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 分页获取用户列表
     *
     * @param page    分页参数
     * @param keyword 关键词
     * @return 用户列表
     */
    Page<User> getUserList(Page<User> page, String keyword);
}