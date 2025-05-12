package com.ncwu.studygo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncwu.studygo.entity.Seat;
import com.ncwu.studygo.mapper.SeatMapper;
import com.ncwu.studygo.service.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 座位服务实现类
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements SeatService {

    @Override
    public List<Seat> getSeatsByRoomId(Long roomId, String date, Integer status) {
        LambdaQueryWrapper<Seat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seat::getRoomId, roomId);
        
        // 如果指定了状态，则按状态筛选
        if (status != null) {
            queryWrapper.eq(Seat::getStatus, status);
        }
        
        // 日期筛选逻辑可以在这里添加，根据具体需求实现
        // 这里假设date参数是用于特定日期的座位状态查询
        
        return this.list(queryWrapper);
    }

    @Override
    public boolean addSeat(Seat seat) {
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        seat.setCreateTime(now);
        seat.setUpdateTime(now);
        
        // 如果没有设置状态，默认为可用状态(0)
        if (seat.getStatus() == null) {
            seat.setStatus(0);
        }
        
        return this.save(seat);
    }

    @Override
    public boolean updateSeat(Seat seat) {
        // 更新时间
        seat.setUpdateTime(LocalDateTime.now());
        return this.updateById(seat);
    }

    @Override
    public boolean updateSeatStatus(Long id, Integer status) {
        LambdaUpdateWrapper<Seat> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Seat::getId, id)
                .set(Seat::getStatus, status)
                .set(Seat::getUpdateTime, LocalDateTime.now());
        
        return this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSetSeats(Long roomId, Integer startNumber, Integer endNumber, Integer status) {
        // 验证参数
        if (roomId == null || startNumber == null || endNumber == null || status == null) {
            return false;
        }
        
        if (startNumber > endNumber) {
            return false;
        }
        
        // 批量设置座位状态
        List<Seat> seatList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = startNumber; i <= endNumber; i++) {
            // 先查询是否存在该座位
            LambdaQueryWrapper<Seat> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Seat::getRoomId, roomId)
                    .eq(Seat::getSeatNumber, String.valueOf(i));
            
            Seat existingSeat = this.getOne(queryWrapper, false);
            
            if (existingSeat != null) {
                // 更新已存在的座位
                existingSeat.setStatus(status);
                existingSeat.setUpdateTime(now);
                this.updateById(existingSeat);
            } else {
                // 创建新座位
                Seat newSeat = new Seat();
                newSeat.setRoomId(roomId);
                newSeat.setSeatNumber(String.valueOf(i));
                newSeat.setStatus(status);
                newSeat.setCreateTime(now);
                newSeat.setUpdateTime(now);
                seatList.add(newSeat);
            }
        }
        
        // 批量保存新创建的座位
        if (!seatList.isEmpty()) {
            this.saveBatch(seatList);
        }
        
        return true;
    }

    @Override
    public List<Seat> getAllSeats() {
        LambdaQueryWrapper<Seat> queryWrapper = new LambdaQueryWrapper<>();
        // 可以根据需要添加排序
        queryWrapper.orderByAsc(Seat::getRoomId)
                .orderByAsc(Seat::getSeatNumber);
        return this.list(queryWrapper);
    }

    @Override
    public boolean deleteSeatById(Long id) {
        if (id == null) {
            return false;
        }

        try {
            // 检查座位是否存在
            if (this.getById(id) == null) {
                return false;
            }

            // 执行删除操作
            return this.removeById(id);
        } catch (Exception e) {
            return false;
        }
    }
}