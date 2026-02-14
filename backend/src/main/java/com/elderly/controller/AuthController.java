package com.elderly.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.elderly.common.Result;
import com.elderly.config.JwtUtil;
import com.elderly.entity.SysLog;
import com.elderly.entity.SysUser;
import com.elderly.service.SysLogService;
import com.elderly.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final SysUserService sysUserService;
    private final SysLogService sysLogService;
    private final JwtUtil jwtUtil;

    @Data
    public static class LoginDTO {
        @NotBlank(message = "用户名不能为空")
        private String username;
        @NotBlank(message = "密码不能为空")
        private String password;
    }

    @PostMapping("/login")
    public Result<?> login(@Valid @RequestBody LoginDTO dto, HttpServletRequest request) {
        SysUser user = sysUserService.findByUsername(dto.getUsername());
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (user.getStatus() != 1) {
            return Result.error("账号已被禁用");
        }
        // 仅使用BCrypt哈希校验
        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            return Result.error("密码错误");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        
        // 记录登录日志
        SysLog sysLog = new SysLog();
        sysLog.setUserId(user.getId());
        sysLog.setUsername(user.getUsername());
        sysLog.setOperation("用户登录");
        sysLog.setMethod("com.elderly.controller.AuthController.login");
        sysLog.setParams("{\"username\":\"" + dto.getUsername() + "\"}");
        sysLog.setIp(getIpAddress(request));
        sysLogService.save(sysLog);
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return Result.success(result);
    }
    
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @GetMapping("/info")
    public Result<?> info(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        SysUser user = sysUserService.findByUsername(username);
        user.setPassword(null);
        return Result.success(user);
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        return Result.success();
    }
}
