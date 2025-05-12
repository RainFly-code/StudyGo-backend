package com.ncwu.studygo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncwu.studygo.entity.User;
import com.ncwu.studygo.mapper.UserMapper;
import com.ncwu.studygo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        if (this.count(queryWrapper) > 0) {
            return false;
        }

        // 设置默认角色为普通用户
        user.setRole(0);
        // 设置创建时间
        user.setCreateTime(LocalDateTime.now());
        // 密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        // 保存用户信息
        return this.save(user);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户信息，失败返回null
     */
    @Override
    public User login(String username, String password) {
        // 查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = this.getOne(queryWrapper);

        // 用户不存在或密码错误
        if (user == null || !user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            return null;
        }

        // 返回用户信息（不包含密码）
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setEmail(user.getEmail());
        safeUser.setPhone(user.getPhone());
        safeUser.setRole(user.getRole());
        safeUser.setGender(user.getGender());
        safeUser.setAge(user.getAge());
        
        return safeUser;
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public User getUserInfo(Long id) {
        User user = this.getById(id);
        if (user != null) {
            // 不返回密码
            user.setPassword(null);
        }
        return user;
    }

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 更新结果
     */
    @Override
    public boolean updateUserInfo(User user) {
        // 不允许修改用户名和密码
        user.setUsername(null);
        user.setPassword(null);
        user.setRole(null);
        return this.updateById(user);
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        // 查询用户
        User user = this.getById(userId);
        if (user == null) {
            return false;
        }

        // 验证旧密码
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes()))) {
            return false;
        }

        // 更新密码
        user.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        return this.updateById(user);
    }

    /**
     * 分页获取用户列表
     *
     * @param page    分页参数
     * @param keyword 关键词
     * @return 用户列表
     */
    @Override
    public Page<User> getUserList(Page<User> page, String keyword) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // 关键词搜索
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(User::getUsername, keyword)
                    .or().like(User::getEmail, keyword)
                    .or().like(User::getPhone, keyword);
        }
        // 排序
        queryWrapper.orderByDesc(User::getCreateTime);
        // 分页查询
        Page<User> userPage = this.page(page, queryWrapper);
        // 不返回密码
        userPage.getRecords().forEach(u -> u.setPassword(null));
        return userPage;
    }
}