package com.ncwu.studygo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ncwu.studygo.entity.StudyRoom;

/**
 * 自习室服务接口
 */
public interface StudyRoomService extends IService<StudyRoom> {

    /**
     * 分页获取自习室列表
     *
     * @param page   分页参数
     * @param status 状态（可选）
     * @return 自习室列表
     */
    Page<StudyRoom> getStudyRoomList(Page<StudyRoom> page, Integer status);

    /**
     * 获取自习室详情
     *
     * @param id 自习室ID
     * @return 自习室详情
     */
    StudyRoom getStudyRoomDetail(Long id);

    /**
     * 模糊查询自习室
     *
     * @param page    分页参数
     * @param keyword 关键词
     * @return 自习室列表
     */
    Page<StudyRoom> searchStudyRoom(Page<StudyRoom> page, String keyword);

    /**
     * 添加自习室
     *
     * @param studyRoom 自习室信息
     * @return 添加结果
     */
    boolean addStudyRoom(StudyRoom studyRoom);

    /**
     * 更新自习室信息
     *
     * @param studyRoom 自习室信息
     * @return 更新结果
     */
    boolean updateStudyRoom(StudyRoom studyRoom);

    /**
     * 更新自习室状态
     *
     * @param id     自习室ID
     * @param status 状态 0:可用 1:维护中
     * @return 更新结果
     */
    boolean updateStudyRoomStatus(Long id, Integer status);
}