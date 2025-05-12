package com.ncwu.studygo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ncwu.studygo.entity.StudyRoom;
import com.ncwu.studygo.mapper.StudyRoomMapper;
import com.ncwu.studygo.service.StudyRoomService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 自习室服务实现类
 */
@Service
public class StudyRoomServiceImpl extends ServiceImpl<StudyRoomMapper, StudyRoom> implements StudyRoomService {

    /**
     * 分页获取自习室列表
     *
     * @param page   分页参数
     * @param status 状态（可选）
     * @return 自习室列表
     */
    @Override
    public Page<StudyRoom> getStudyRoomList(Page<StudyRoom> page, Integer status) {
        LambdaQueryWrapper<StudyRoom> queryWrapper = new LambdaQueryWrapper<>();
        // 根据状态筛选
        if (status != null) {
            queryWrapper.eq(StudyRoom::getStatus, status);
        }
        // 按名称排序
        queryWrapper.orderByAsc(StudyRoom::getName);
        return this.page(page, queryWrapper);
    }

    /**
     * 获取自习室详情
     *
     * @param id 自习室ID
     * @return 自习室详情
     */
    @Override
    public StudyRoom getStudyRoomDetail(Long id) {
        return this.getById(id);
    }

    /**
     * 模糊查询自习室
     *
     * @param page    分页参数
     * @param keyword 关键词
     * @return 自习室列表
     */
    @Override
    public Page<StudyRoom> searchStudyRoom(Page<StudyRoom> page, String keyword) {
        LambdaQueryWrapper<StudyRoom> queryWrapper = new LambdaQueryWrapper<>();
        // 关键词搜索
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(StudyRoom::getName, keyword)
                    .or().like(StudyRoom::getDescription, keyword);
        }
        // 只查询可用的自习室
        queryWrapper.eq(StudyRoom::getStatus, 0);
        // 按名称排序
        queryWrapper.orderByAsc(StudyRoom::getName);
        return this.page(page, queryWrapper);
    }

    /**
     * 添加自习室
     *
     * @param studyRoom 自习室信息
     * @return 添加结果
     */
    @Override
    public boolean addStudyRoom(StudyRoom studyRoom) {
        // 设置默认状态为可用
        if (studyRoom.getStatus() == null) {
            studyRoom.setStatus(0);
        }
        // 设置创建时间
        studyRoom.setCreateTime(LocalDateTime.now());
        return this.save(studyRoom);
    }

    /**
     * 更新自习室信息
     *
     * @param studyRoom 自习室信息
     * @return 更新结果
     */
    @Override
    public boolean updateStudyRoom(StudyRoom studyRoom) {
        return this.updateById(studyRoom);
    }

    /**
     * 更新自习室状态
     *
     * @param id     自习室ID
     * @param status 状态 0:可用 1:维护中
     * @return 更新结果
     */
    @Override
    public boolean updateStudyRoomStatus(Long id, Integer status) {
        StudyRoom studyRoom = new StudyRoom();
        studyRoom.setId(id);
        studyRoom.setStatus(status);
        return this.updateById(studyRoom);
    }
}