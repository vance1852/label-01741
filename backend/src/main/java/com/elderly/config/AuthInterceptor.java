package com.elderly.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.elderly.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (StrUtil.isBlank(token)) {
            writeErrorResponse(response, 401, "未登录");
            return false;
        }
        token = token.replace("Bearer ", "");
        if (!jwtUtil.validateToken(token)) {
            writeErrorResponse(response, 401, "token无效或已过期");
            return false;
        }
        request.setAttribute("userId", jwtUtil.getUserId(token));
        request.setAttribute("username", jwtUtil.getUsername(token));
        return true;
    }
    
    private void writeErrorResponse(HttpServletResponse response, int code, String message) throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(Result.error(code, message)));
    }
}
