package com.ncwu.studygo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncwu.studygo.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

/**
 * 预约服务接口
 */
public interface ReservationService extends IService<Reservation> {

    /**
     * 创建预约
     *
     * @param seatId 座位ID
     * @param userId 用户ID
     * @param date   预约日期
     * @param days   预约天数
     * @return 预约ID
     */
    Long addReservation(Long seatId, Long userId, LocalDate date, Integer days);

    /**
     * 获取用户预约列表
     *
     * @param userId 用户ID
     * @param status 状态（可选）
     * @param page   页码
     * @param size   每页数量
     * @return 预约分页列表
     */
    IPage<Reservation> getUserReservations(Long userId, Integer status, Integer page, Integer size);

    /**
     * 获取预约详情
     *
     * @param id 预约ID
     * @return 预约信息
     */
    Reservation getReservationDetail(Long id);

    /**
     * 取消预约
     *
     * @param id     预约ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean cancelReservation(Long id, Long userId);

    /**
     * 管理员获取预约列表
     *
     * @param userId    用户ID（可选）
     * @param roomId    自习室ID（可选）
     * @param status    状态（可选）
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @param page      页码
     * @param size      每页数量
     * @return 预约分页列表
     */
    IPage<Reservation> getReservationList(Long userId, Long roomId, Integer status,
                                          LocalDate startDate, LocalDate endDate,
                                          Integer page, Integer size);

    /**
     * 管理员处理预约
     *
     * @param id     预约ID
     * @param status 状态
     * @return 是否成功
     */
    boolean handleReservation(Long id, Integer status);

    /**
     * 导出预约记录
     *
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @param roomId    自习室ID（可选）
     * @param status    状态（可选）
     * @return 预约列表
     */
    List<Reservation> exportReservations(LocalDate startDate, LocalDate endDate,
                                         Long roomId, Integer status);
} 