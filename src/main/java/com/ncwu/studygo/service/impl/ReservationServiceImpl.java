package com.ncwu.studygo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncwu.studygo.entity.Reservation;
import com.ncwu.studygo.entity.Seat;
import com.ncwu.studygo.entity.StudyRoom;
import com.ncwu.studygo.mapper.ReservationMapper;
import com.ncwu.studygo.service.ReservationService;
import com.ncwu.studygo.service.SeatService;
import com.ncwu.studygo.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 预约服务实现类
 */
@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

    @Autowired
    private SeatService seatService;
    
    @Autowired
    private StudyRoomService studyRoomService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addReservation(Long seatId, Long userId, LocalDate date, Integer days) {
        // 检查座位是否存在
        Seat seat = seatService.getById(seatId);
        if (seat == null) {
            throw new RuntimeException("座位不存在");
        }

        // 检查座位状态是否可用
        if (seat.getStatus() != 0) {
            throw new RuntimeException("座位不可用");
        }
        
        // 获取自习室信息
        StudyRoom studyRoom = studyRoomService.getById(seat.getRoomId());
        if (studyRoom == null) {
            throw new RuntimeException("自习室不存在");
        }
        
        // 计算预约结束日期
        LocalDate endDate = date.plusDays(days - 1);
        
        // 检查该时间段是否已被预约，使用XML中定义的方法
        int count = baseMapper.checkDateOverlap(seatId, date, endDate);
        if (count > 0) {
            throw new RuntimeException("该时间段座位已被预约，请选择其他日期或座位");
        }

        // 创建预约
        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setSeatId(seatId);
        reservation.setRoomId(seat.getRoomId());
        reservation.setSeatNumber(seat.getSeatNumber());
        reservation.setRoomName(studyRoom.getName());
        reservation.setDate(date);
        reservation.setDays(days);
        reservation.setStatus(0); // 设置状态为待审核
        reservation.setCreateTime(LocalDateTime.now());

        baseMapper.insert(reservation);
        return reservation.getId();
    }

    @Override
    public IPage<Reservation> getUserReservations(Long userId, Integer status, Integer page, Integer size) {
        LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Reservation::getUserId, userId);
        if (status != null) {
            queryWrapper.eq(Reservation::getStatus, status);
        }
        queryWrapper.orderByDesc(Reservation::getCreateTime);

        return baseMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    @Override
    public Reservation getReservationDetail(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelReservation(Long id, Long userId) {
        // 获取预约信息
        Reservation reservation = baseMapper.selectById(id);
        if (reservation == null) {
            throw new RuntimeException("预约不存在");
        }

        // 验证是否为当前用户的预约
        if (!reservation.getUserId().equals(userId)) {
            throw new RuntimeException("无权取消该预约");
        }

        // 验证预约是否已开始
        if (LocalDate.now().isAfter(reservation.getDate())) {
            throw new RuntimeException("预约已经开始，无法取消");
        }

        // 更新预约状态为已取消
        reservation.setStatus(3); // 设置状态为已取消
        reservation.setUpdateTime(LocalDateTime.now());
        
        return baseMapper.updateById(reservation) > 0;
    }

    @Override
    public IPage<Reservation> getReservationList(Long userId, Long roomId, Integer status, 
                                                LocalDate startDate, LocalDate endDate, 
                                                Integer page, Integer size) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        
        if (userId != null) {
            queryWrapper.eq("userId", userId);
        }
        
        if (roomId != null) {
            queryWrapper.eq("roomId", roomId);
        }
        
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        
        if (startDate != null && endDate != null) {
            queryWrapper.and(w -> 
                w.between("date", startDate, endDate)
                .or()
                .le("date", startDate).ge("date + days - 1", endDate)
            );
        } else if (startDate != null) {
            queryWrapper.ge("date", startDate);
        } else if (endDate != null) {
            queryWrapper.le("date + days - 1", endDate);
        }
        
        queryWrapper.orderByDesc("createTime");
        
        return baseMapper.selectPage(new Page<>(page, size), queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleReservation(Long id, Integer status) {
        // 获取预约信息
        Reservation reservation = baseMapper.selectById(id);
        if (reservation == null) {
            throw new RuntimeException("预约不存在");
        }
        
        // 更新预约状态
        reservation.setStatus(status);
        reservation.setUpdateTime(LocalDateTime.now());
        
        // 如果预约被通过，更新座位状态为已预约
        if (status == 1) {
            Seat seat = seatService.getById(reservation.getSeatId());
            seat.setStatus(1); // 更新为已预约状态
            seatService.updateById(seat);
        }
        
        return baseMapper.updateById(reservation) > 0;
    }

    @Override
    public List<Reservation> exportReservations(LocalDate startDate, LocalDate endDate, Long roomId, Integer status) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        
        if (roomId != null) {
            queryWrapper.eq("roomId", roomId);
        }
        
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        
        if (startDate != null && endDate != null) {
            queryWrapper.and(w -> 
                w.between("date", startDate, endDate)
                .or()
                .le("date", startDate).ge("date + days - 1", endDate)
            );
        } else if (startDate != null) {
            queryWrapper.ge("date", startDate);
        } else if (endDate != null) {
            queryWrapper.le("date + days - 1", endDate);
        }
        
        queryWrapper.orderByDesc("createTime");
        
        return baseMapper.selectList(queryWrapper);
    }
} 