package com.ncwu.studygo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncwu.studygo.common.result.Result;
import com.ncwu.studygo.entity.User;
import com.ncwu.studygo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/user/register")
    public Result<Object> register(@RequestBody User user) {
        if (userService.register(user)) {
            return Result.success("注册成功");
        } else {
            return Result.failed("注册失败，用户名已存在");
        }
    }

    /**
     * 用户登录
     *
     * @return 登录结果
     */
    @PostMapping("/user/login")
    public Result<Object> login(@RequestBody User user, HttpServletRequest request) {
        // 调用service层进行用户验证
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            // 将用户信息存入会话
            request.getSession().setAttribute("userId", loginUser.getId());
            request.getSession().setAttribute("username", loginUser.getUsername());
            request.getSession().setAttribute("role", loginUser.getRole());
            
            Map<String, Object> data = new HashMap<>();
            data.put("user", loginUser);
            return Result.success(data, "登录成功");
        } else {
            return Result.failed("用户名或密码错误");
        }
    }

    /**
     * 获取用户信息
     *
     * @param request 请求对象
     * @return 用户信息
     */
    @GetMapping("/user/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        User user = userService.getUserInfo(userId);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.failed("获取用户信息失败");
        }
    }

    /**
     * 更新用户信息
     *
     * @param user    用户信息
     * @param request 请求对象
     * @return 更新结果
     */
    @PutMapping("/user/update")
    public Result<Object> updateUserInfo(@RequestBody User user, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        user.setId(userId);
        if (userService.updateUserInfo(user)) {
            return Result.success("更新成功");
        } else {
            return Result.failed("更新失败");
        }
    }

    /**
     * 修改密码
     *
     * @param params  密码参数
     * @param request 请求对象
     * @return 修改结果
     */
    @PutMapping("/user/password")
    public Result<Object> updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        if (userService.updatePassword(userId, oldPassword, newPassword)) {
            return Result.success("密码修改成功");
        } else {
            return Result.failed("密码修改失败，旧密码错误");
        }
    }

    /**
     * 管理员获取用户列表
     *
     * @param page    页码
     * @param size    每页数量
     * @param keyword 关键词
     * @return 用户列表
     */
    @GetMapping("/admin/user/list")
    public Result<Page<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword) {
        Page<User> userPage = userService.getUserList(new Page<>(page, size), keyword);
        return Result.success(userPage);
    }

    /**
     * 管理员获取用户详情
     *
     * @param id 用户ID
     * @return 用户详情
     */
    @GetMapping("/admin/user/{id}")
    public Result<User> getUserDetail(@PathVariable Long id) {
        User user = userService.getUserInfo(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.failed("用户不存在");
        }
    }

    /**
     * 管理员更新用户信息
     *
     * @param id   用户ID
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/admin/user/{id}")
    public Result<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        // 管理员可以修改角色
        if (userService.updateById(user)) {
            return Result.success("更新成功");
        } else {
            return Result.failed("更新失败");
        }
    }

    /**
     * 管理员删除用户
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/admin/user/{id}")
    public Result<Object> deleteUser(@PathVariable Long id) {
        if (userService.removeById(id)) {
            return Result.success("删除成功");
        } else {
            return Result.failed("删除失败");
        }
    }

    /**
     * 管理员添加用户
     *
     * @param user 用户信息
     * @return 添加结果
     */
    @PostMapping("/admin/user/add")
    public Result<Object> addUser(@RequestBody User user) {
        if (userService.register(user)) {
            return Result.success("添加成功");
        } else {
            return Result.failed("添加失败，用户名已存在");
        }
    }
}