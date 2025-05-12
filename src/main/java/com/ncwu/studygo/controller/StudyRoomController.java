package com.ncwu.studygo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ncwu.studygo.common.result.Result;
import com.ncwu.studygo.entity.Seat;
import com.ncwu.studygo.entity.StudyRoom;
import com.ncwu.studygo.service.SeatService;
import com.ncwu.studygo.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 自习室控制器
 */
@RestController
@RequestMapping("/api")
public class StudyRoomController {

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private SeatService seatService;

    /**
     * 获取自习室列表
     *
     * @param page   页码
     * @param size   每页数量
     * @param status 状态（可选）
     * @return 自习室列表
     */
    @GetMapping("/studyroom/list")
    public Result<Page<StudyRoom>> getStudyRoomList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status) {
        Page<StudyRoom> studyRoomPage = studyRoomService.getStudyRoomList(new Page<>(page, size), status);
        return Result.success(studyRoomPage);
    }

    /**
     * 获取自习室详情
     *
     * @param id 自习室ID
     * @return 自习室详情
     */
    @GetMapping("/studyroom/{id}")
    public Result<StudyRoom> getStudyRoomDetail(@PathVariable Long id) {
        StudyRoom studyRoom = studyRoomService.getStudyRoomDetail(id);
        if (studyRoom != null) {
            return Result.success(studyRoom);
        } else {
            return Result.failed("自习室不存在");
        }
    }

    /**
     * 模糊查询自习室
     *
     * @param keyword 关键词
     * @param page    页码
     * @param size    每页数量
     * @return 自习室列表
     */
    @GetMapping("/studyroom/search")
    public Result<Page<StudyRoom>> searchStudyRoom(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<StudyRoom> studyRoomPage = studyRoomService.searchStudyRoom(new Page<>(page, size), keyword);
        return Result.success(studyRoomPage);
    }

    /**
     * 获取自习室座位列表
     *
     * @param id     自习室ID
     * @param date   日期（可选）
     * @param status 状态（可选）
     * @return 座位列表
     */
    @GetMapping("/studyroom/{id}/seats")
    public Result<List<Seat>> getStudyRoomSeats(
            @PathVariable Long id,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Integer status) {
        List<Seat> seats = seatService.getSeatsByRoomId(id, date, status);
        return Result.success(seats);
    }

    /**
     * 添加自习室
     *
     * @param studyRoom 自习室信息
     * @return 添加结果
     */
    @PostMapping("/admin/studyroom/add")
    public Result<Object> addStudyRoom(@RequestBody StudyRoom studyRoom) {
        if (studyRoomService.addStudyRoom(studyRoom)) {
            return Result.success("添加成功");
        } else {
            return Result.failed("添加失败");
        }
    }

    /**
     * 更新自习室信息
     *
     * @param id        自习室ID
     * @param studyRoom 自习室信息
     * @return 更新结果
     */
    @PutMapping("/admin/studyroom/{id}")
    public Result<Object> updateStudyRoom(@PathVariable Long id,@RequestBody StudyRoom studyRoom) {
        studyRoom.setId(id);
        if (studyRoomService.updateStudyRoom(studyRoom)) {
            return Result.success("更新成功");
        } else {
            return Result.failed("更新失败");
        }
    }

    /**
     * 删除自习室
     *
     * @param id 自习室ID
     * @return 删除结果
     */
    @DeleteMapping("/admin/studyroom/{id}")
    public Result<Object> deleteStudyRoom(@PathVariable Long id) {
        if (studyRoomService.removeById(id)) {
            return Result.success("删除成功");
        } else {
            return Result.failed("删除失败");
        }
    }

    /**
     * 更新自习室状态
     *
     * @param id     自习室ID
     * @param status 状态
     * @return 更新结果
     */
    @PutMapping("/admin/studyroom/{id}/status")
    public Result<Object> updateStudyRoomStatus(@PathVariable Long id, @RequestParam Integer status) {
        if (status == null) {
            return Result.failed("状态不能为空");
        }
        if (studyRoomService.updateStudyRoomStatus(id, status)) {
            return Result.success("更新成功");
        } else {
            return Result.failed("更新失败");
        }
    }
}