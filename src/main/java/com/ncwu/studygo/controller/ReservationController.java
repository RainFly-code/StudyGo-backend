package com.ncwu.studygo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ncwu.studygo.common.result.Result;
import com.ncwu.studygo.entity.Reservation;
import com.ncwu.studygo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约管理控制器
 */
@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * 预约座位
     */
    @PostMapping("/reservation/add")
    public Result<Map<String, Object>> addReservation(
            @RequestParam Long seatId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam Integer days,
            HttpSession session) {
        // 从session中获取用户ID
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.unauthorized();
        }

        // 创建预约
        Long reservationId = reservationService.addReservation(seatId, userId, date, days);

        // 返回预约ID
        Map<String, Object> result = new HashMap<>();
        result.put("id", reservationId);
        return Result.success(result, "预约提交成功，等待审核");
    }

    /**
     * 获取个人预约列表
     */
    @GetMapping("/reservation/list")
    public Result<IPage<Reservation>> getUserReservations(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status,
            HttpSession session) {
        // 从session中获取用户ID
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.unauthorized();
        }

        // 获取预约列表
        IPage<Reservation> reservationList = reservationService.getUserReservations(userId, status, page, size);
        return Result.success(reservationList);
    }

    /**
     * 获取预约详情
     */
    @GetMapping("/reservation/{id}")
    public Result<Reservation> getReservationDetail(
            @PathVariable Long id,
            HttpSession session) {
        // 从session中获取用户ID
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.unauthorized();
        }

        // 获取预约详情
        Reservation reservation = reservationService.getReservationDetail(id);
        
        // 判断是否为当前用户的预约
        if (reservation != null && !reservation.getUserId().equals(userId) && !isAdmin(session)) {
            return Result.forbidden();
        }

        return Result.success(reservation);
    }

    /**
     * 取消预约
     */
    @PutMapping("/reservation/{id}/cancel")
    public Result<Boolean> cancelReservation(
            @PathVariable Long id,
            HttpSession session) {
        // 从session中获取用户ID
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.unauthorized();
        }

        // 取消预约
        boolean result = reservationService.cancelReservation(id, userId);
        return Result.success(result, "预约已取消");
    }

    /**
     * 管理员获取预约列表
     */
    @GetMapping("/admin/reservation/list")
    public Result<IPage<Reservation>> getReservationList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 获取预约列表
        IPage<Reservation> reservationList = reservationService.getReservationList(
                userId, roomId, status, startDate, endDate, page, size);
        return Result.success(reservationList);
    }

    /**
     * 管理员处理预约
     */
    @PutMapping("/admin/reservation/{id}")
    public Result<Boolean> handleReservation(
            @PathVariable Long id,
            @RequestParam Integer status,
            HttpSession session) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 处理预约
        boolean result = reservationService.handleReservation(id, status);
        
        String message;
        switch (status) {
            case 1:
                message = "预约已通过";
                break;
            case 2:
                message = "预约已拒绝";
                break;
            case 3:
                message = "预约已取消";
                break;
            default:
                message = "预约状态已更新";
        }
        
        return Result.success(result, message);
    }

    /**
     * 导出预约记录
     */
    @GetMapping("/admin/reservation/export")
    public Result<List<Reservation>> exportReservations(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) Integer status,
            HttpSession session,
            HttpServletResponse response) {
        // 验证管理员权限
        if (!isAdmin(session)) {
            return Result.forbidden();
        }

        // 导出预约记录
        List<Reservation> reservations = reservationService.exportReservations(
                startDate, endDate, roomId, status);
                
        // 这里应该加入导出到Excel的实现，简化起见，直接返回数据
        return Result.success(reservations);
    }
    
    /**
     * 判断当前用户是否为管理员
     * @param session HTTP会话
     * @return 是否为管理员
     */
    private boolean isAdmin(HttpSession session) {
        Integer role = (Integer) session.getAttribute("role");
        return role != null && role == 1; // 假设role=1为管理员
    }
} 