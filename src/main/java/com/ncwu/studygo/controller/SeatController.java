package com.ncwu.studygo.controller;

import com.ncwu.studygo.entity.Seat;
import com.ncwu.studygo.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 座位控制器
 */
@RestController
@RequestMapping("/api")
public class SeatController {

    @Autowired
    private SeatService seatService;
    /**
     * 管理员添加座位
     *
     * @param seat 座位信息
     * @return 添加结果
     */
    @PostMapping("/admin/seat/add")
    public ResponseEntity<Map<String, Object>> addSeat(@RequestBody Seat seat) {
        Map<String, Object> result = new HashMap<>();
        boolean success = seatService.addSeat(seat);
        
        if (success) {
            result.put("success", true);
            result.put("message", "座位添加成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "座位添加失败");
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 管理员获取所有座位信息
     *
     * @return 所有座位列表
     */
    @GetMapping("/admin/seat/all")
    public ResponseEntity<Map<String, Object>> getAllSeats() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Seat> seats = seatService.getAllSeats();
            result.put("code", 200);
            result.put("message", "获取所有座位成功");
            result.put("data", seats);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "获取所有座位失败: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    /**
     * 管理员更新座位信息
     *
     * @param id   座位ID
     * @param seat 座位信息
     * @return 更新结果
     */
    @PutMapping("/admin/seat/{id}")
    public ResponseEntity<Map<String, Object>> updateSeat(
            @PathVariable Long id,
            Seat seat) {
        Map<String, Object> result = new HashMap<>();
        
        // 确保ID一致
        seat.setId(id);
        boolean success = seatService.updateSeat(seat);
        
        if (success) {
            result.put("success", true);
            result.put("message", "座位信息更新成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "座位信息更新失败");
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 管理员更新座位状态
     *
     * @param id     座位ID
     * @param status 状态 0:可用 1:已预约 2:维护中
     * @return 更新结果
     */
    @PutMapping("/admin/seat/{id}/status")
    public ResponseEntity<Map<String, Object>> updateSeatStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        boolean success = seatService.updateSeatStatus(id, status);
        
        if (success) {
            result.put("success", true);
            result.put("message", "座位状态更新成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "座位状态更新失败");
            return ResponseEntity.badRequest().body(result);
        }
    }

    /**
     * 管理员批量设置座位信息
     *
     * @param roomId      自习室ID
     * @param startNumber 起始编号
     * @param endNumber   结束编号
     * @param status      状态 0:可用 1:已预约 2:维护中
     * @return 设置结果
     */
    @PostMapping("/admin/seat/batch")
    public ResponseEntity<Map<String, Object>> batchSetSeats(
            @RequestParam Long roomId,
            @RequestParam Integer startNumber,
            @RequestParam Integer endNumber,
            @RequestParam Integer status) {
        Map<String, Object> result = new HashMap<>();
        boolean success = seatService.batchSetSeats(roomId, startNumber, endNumber, status);
        
        if (success) {
            result.put("success", true);
            result.put("message", "批量设置座位成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "批量设置座位失败");
            return ResponseEntity.badRequest().body(result);
        }
    }
    @PostMapping("/admin/seat/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteSeat(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = seatService.deleteSeatById(id);

        if (success) {
            result.put("success", true);
            result.put("message", "座位删除成功");
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "座位删除失败");
            return ResponseEntity.badRequest().body(result);
        }
    }
}