package com.elderly;

import com.elderly.common.Result;
import com.elderly.config.JwtUtil;
import com.elderly.entity.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private static String token;

    @BeforeEach
    void setUp() {
        if (token == null) {
            token = jwtUtil.generateToken(1L, "admin", 1);
        }
    }

    // ==================== 认证模块测试 ====================

    @Test
    @Order(1)
    @DisplayName("登录接口测试 - 正确凭证")
    void testLoginSuccess() throws Exception {
        String loginJson = "{\"username\":\"admin\",\"password\":\"admin123\"}";
        
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").exists())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println("登录成功响应: " + response);
    }

    @Test
    @Order(2)
    @DisplayName("登录接口测试 - 错误密码")
    void testLoginWrongPassword() throws Exception {
        String loginJson = "{\"username\":\"admin\",\"password\":\"wrongpassword\"}";
        
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500));
    }

    @Test
    @Order(3)
    @DisplayName("获取用户信息接口测试")
    void testGetUserInfo() throws Exception {
        mockMvc.perform(get("/api/auth/info")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.username").value("admin"));
    }

    // ==================== 老人管理模块测试 ====================

    @Test
    @Order(10)
    @DisplayName("老人分页查询测试")
    void testElderPage() throws Exception {
        mockMvc.perform(get("/api/elder/page")
                .header("Authorization", "Bearer " + token)
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    @Test
    @Order(11)
    @DisplayName("老人列表查询测试")
    void testElderList() throws Exception {
        mockMvc.perform(get("/api/elder/list")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(12)
    @DisplayName("新增老人测试")
    void testAddElder() throws Exception {
        Elder elder = new Elder();
        elder.setName("测试老人");
        elder.setGender(1);
        elder.setBirthDate(LocalDate.of(1950, 1, 1));
        elder.setPhone("13900000001");
        elder.setAddress("测试地址");
        elder.setEmergencyContact("测试联系人");
        elder.setEmergencyPhone("13900000002");
        elder.setHealthStatus(1);

        mockMvc.perform(post("/api/elder")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(elder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(13)
    @DisplayName("查询老人详情测试")
    void testGetElderById() throws Exception {
        mockMvc.perform(get("/api/elder/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @Order(14)
    @DisplayName("更新老人信息测试")
    void testUpdateElder() throws Exception {
        Elder elder = new Elder();
        elder.setId(1L);
        elder.setName("张福贵更新");
        elder.setGender(1);
        elder.setPhone("13900139001");

        mockMvc.perform(put("/api/elder")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(elder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // ==================== 护工管理模块测试 ====================

    @Test
    @Order(20)
    @DisplayName("护工分页查询测试")
    void testWorkerPage() throws Exception {
        mockMvc.perform(get("/api/worker/page")
                .header("Authorization", "Bearer " + token)
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    @Test
    @Order(21)
    @DisplayName("护工列表查询测试")
    void testWorkerList() throws Exception {
        mockMvc.perform(get("/api/worker/list")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(22)
    @DisplayName("新增护工测试")
    void testAddWorker() throws Exception {
        CareWorker worker = new CareWorker();
        worker.setName("测试护工");
        worker.setGender(2);
        worker.setPhone("13800000001");
        worker.setSkills("测试技能");

        mockMvc.perform(post("/api/worker")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(worker)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(23)
    @DisplayName("更新护工信息测试")
    void testUpdateWorker() throws Exception {
        CareWorker worker = new CareWorker();
        worker.setId(1L);
        worker.setName("王美玲更新");
        worker.setGender(2);
        worker.setPhone("13800138001");
        worker.setSkills("日常护理、康复训练、心理疏导");

        mockMvc.perform(put("/api/worker")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(worker)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // ==================== 服务项目模块测试 ====================

    @Test
    @Order(30)
    @DisplayName("服务项目分页查询测试")
    void testServicePage() throws Exception {
        mockMvc.perform(get("/api/service/page")
                .header("Authorization", "Bearer " + token)
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    @Test
    @Order(31)
    @DisplayName("服务项目列表查询测试")
    void testServiceList() throws Exception {
        mockMvc.perform(get("/api/service/list")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(32)
    @DisplayName("新增服务项目测试")
    void testAddService() throws Exception {
        ServiceItem service = new ServiceItem();
        service.setName("测试服务");
        service.setDescription("测试服务描述");
        service.setPrice(new BigDecimal("99.00"));
        service.setDuration(30);

        mockMvc.perform(post("/api/service")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(33)
    @DisplayName("更新服务项目测试")
    void testUpdateService() throws Exception {
        ServiceItem service = new ServiceItem();
        service.setId(1L);
        service.setName("日常生活照料更新");
        service.setDescription("更新后的描述");
        service.setPrice(new BigDecimal("120.00"));
        service.setDuration(60);

        mockMvc.perform(put("/api/service")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // ==================== 服务订单模块测试 ====================

    @Test
    @Order(40)
    @DisplayName("订单分页查询测试")
    void testOrderPage() throws Exception {
        mockMvc.perform(get("/api/order/page")
                .header("Authorization", "Bearer " + token)
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    @Test
    @Order(41)
    @DisplayName("创建订单测试")
    void testCreateOrder() throws Exception {
        ServiceOrder order = new ServiceOrder();
        order.setElderId(1L);
        order.setServiceId(1L);
        order.setWorkerId(1L);
        order.setAppointmentTime(LocalDateTime.now().plusDays(1));
        order.setRemark("测试订单备注");

        mockMvc.perform(post("/api/order")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(42)
    @DisplayName("更新订单状态测试")
    void testUpdateOrderStatus() throws Exception {
        String statusJson = "{\"id\":1,\"status\":1}";

        mockMvc.perform(put("/api/order/status")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(statusJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // ==================== 健康记录模块测试 ====================

    @Test
    @Order(50)
    @DisplayName("健康记录分页查询测试")
    void testHealthPage() throws Exception {
        mockMvc.perform(get("/api/health/page")
                .header("Authorization", "Bearer " + token)
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    @Test
    @Order(51)
    @DisplayName("按老人查询健康记录测试")
    void testHealthByElder() throws Exception {
        mockMvc.perform(get("/api/health/elder/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @Order(52)
    @DisplayName("新增健康记录测试")
    void testAddHealthRecord() throws Exception {
        HealthRecord record = new HealthRecord();
        record.setElderId(1L);
        record.setBloodPressureHigh(new BigDecimal("120.0"));
        record.setBloodPressureLow(new BigDecimal("80.0"));
        record.setHeartRate(new BigDecimal("72.0"));
        record.setBloodSugar(new BigDecimal("5.5"));
        record.setTemperature(new BigDecimal("36.5"));
        record.setRemark("测试健康记录");

        mockMvc.perform(post("/api/health")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // ==================== 仪表盘模块测试 ====================

    @Test
    @Order(60)
    @DisplayName("仪表盘统计数据测试")
    void testDashboardStats() throws Exception {
        mockMvc.perform(get("/api/dashboard/stats")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.elderCount").exists())
                .andExpect(jsonPath("$.data.workerCount").exists())
                .andExpect(jsonPath("$.data.pendingOrders").exists())
                .andExpect(jsonPath("$.data.completedOrders").exists());
    }

    // ==================== 系统日志模块测试 ====================

    @Test
    @Order(70)
    @DisplayName("系统日志分页查询测试")
    void testLogPage() throws Exception {
        mockMvc.perform(get("/api/log/page")
                .header("Authorization", "Bearer " + token)
                .param("current", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records").isArray());
    }

    // ==================== 删除操作测试 ====================

    @Test
    @Order(90)
    @DisplayName("删除老人测试")
    void testDeleteElder() throws Exception {
        // 先添加一个用于删除的老人
        Elder elder = new Elder();
        elder.setName("待删除老人");
        elder.setGender(1);
        elder.setPhone("13900000099");

        MvcResult addResult = mockMvc.perform(post("/api/elder")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(elder)))
                .andExpect(status().isOk())
                .andReturn();

        // 删除ID为3的老人（新添加的）
        mockMvc.perform(delete("/api/elder/3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(91)
    @DisplayName("删除护工测试")
    void testDeleteWorker() throws Exception {
        // 先添加一个用于删除的护工
        CareWorker worker = new CareWorker();
        worker.setName("待删除护工");
        worker.setGender(1);
        worker.setPhone("13800000099");

        mockMvc.perform(post("/api/worker")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(worker)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/worker/3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @Order(92)
    @DisplayName("删除服务项目测试")
    void testDeleteService() throws Exception {
        // 先添加一个用于删除的服务
        ServiceItem service = new ServiceItem();
        service.setName("待删除服务");
        service.setPrice(new BigDecimal("50.00"));
        service.setDuration(30);

        mockMvc.perform(post("/api/service")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(service)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/service/3")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    // ==================== 未授权访问测试 ====================

    @Test
    @Order(100)
    @DisplayName("未授权访问测试")
    void testUnauthorizedAccess() throws Exception {
        // 未授权访问时，拦截器返回 401 状态码
        MvcResult result = mockMvc.perform(get("/api/elder/page"))
                .andReturn();
        
        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("401") || content.contains("未登录"), 
                "未授权访问应返回401或未登录提示");
    }
}
