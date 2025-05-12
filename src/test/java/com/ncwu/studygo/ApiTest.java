package com.ncwu.studygo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * API测试类
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest {

    @Autowired
    private MockMvc mockMvc;

    // 模拟Session
    private MockHttpSession session;
    
    @BeforeEach
    public void setUp() {
        // 创建一个模拟会话
        session = new MockHttpSession();
        // 设置用户ID和角色信息
        session.setAttribute("userId", 1L);
        session.setAttribute("roleId", 1); // 设置为管理员角色
    }
    
    /**
     * 测试登录接口
     */
    @Test
    public void testLogin() throws Exception {
        String loginJson = "{\"username\":\"admin\",\"password\":\"123456\"}";
        
        MvcResult result = mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        
        // 输出登录响应
        String response = result.getResponse().getContentAsString();
        System.out.println("登录响应：" + response);
        
        // 在真实环境中，登录成功后会设置session属性
        // 这里我们已经在setUp方法中预设了
    }
    
    /**
     * 测试获取自习室列表
     */
    @Test
    public void testGetStudyRoomList() throws Exception {
        mockMvc.perform(get("/api/studyroom/list")
                .session(session)
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }
    
    /**
     * 测试预约座位
     */
    @Test
    public void testAddReservation() throws Exception {
        mockMvc.perform(post("/api/reservation/add")
                .session(session)
                .param("seatId", "1")
                .param("date", "2023-12-01")
                .param("days", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").exists());
    }
    
    /**
     * 测试获取个人预约列表
     */
    @Test
    public void testGetUserReservations() throws Exception {
        mockMvc.perform(get("/api/reservation/list")
                .session(session)
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }
    
    /**
     * 测试获取公告列表
     */
    @Test
    public void testGetAnnouncementList() throws Exception {
        mockMvc.perform(get("/api/announcement/list")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }
    
    /**
     * 测试提交反馈
     */
    @Test
    public void testAddFeedback() throws Exception {
        String feedbackJson = "{\"type\":1,\"title\":\"测试反馈\",\"content\":\"这是一条测试反馈内容\"}";
        
        mockMvc.perform(post("/api/feedback/add")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(feedbackJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").exists());
    }
    
    /**
     * 测试获取个人反馈列表
     */
    @Test
    public void testGetUserFeedbacks() throws Exception {
        mockMvc.perform(get("/api/feedback/list")
                .session(session)
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }
    
    /**
     * 测试管理员功能 - 获取所有反馈
     */
    @Test
    public void testGetAllFeedbacks() throws Exception {
        mockMvc.perform(get("/api/admin/feedback/list")
                .session(session)
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").exists());
    }
} 