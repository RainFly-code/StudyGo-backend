package com.ncwu.studygo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ncwu.studygo.entity.Seat;

import java.util.List;

/**
 * 座位服务接口
 */
public interface SeatService extends IService<Seat> {

    /**
     * 根据自习室ID获取座位列表
     *
     * @param roomId 自习室ID
     * @param date   日期（可选）
     * @param status 状态（可选）
     * @return 座位列表
     */
    List<Seat> getSeatsByRoomId(Long roomId, String date, Integer status);

    /**
     * 添加座位
     *
     * @param seat 座位信息
     * @return 添加结果
     */
    boolean addSeat(Seat seat);

    /**
     * 更新座位信息
     *
     * @param seat 座位信息
     * @return 更新结果
     */
    boolean updateSeat(Seat seat);

    /**
     * 更新座位状态
     *
     * @param id     座位ID
     * @param status 状态 0:可用 1:已预约 2:维护中
     * @return 更新结果
     */
    boolean updateSeatStatus(Long id, Integer status);

    /**
     * 批量设置座位信息
     *
     * @param roomId      自习室ID
     * @param startNumber 起始编号
     * @param endNumber   结束编号
     * @param status      状态 0:可用 1:已预约 2:维护中
     * @return 设置结果
     */
    boolean batchSetSeats(Long roomId, Integer startNumber, Integer endNumber, Integer status);

    /**
     * 获取所有座位信息
     * @return 所有座位列表
     */
    List<Seat> getAllSeats();

    /**
     * 根据ID删除座位
     * @param id 座位ID
     * @return 是否删除成功
     */
    boolean deleteSeatById(Long id);
}