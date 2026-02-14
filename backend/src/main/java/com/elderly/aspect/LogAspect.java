package com.elderly.aspect;

import cn.hutool.json.JSONUtil;
import com.elderly.entity.SysLog;
import com.elderly.service.SysLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final SysLogService sysLogService;

    @Pointcut("execution(* com.elderly.controller.*.*(..)) && !execution(* com.elderly.controller.AuthController.*(..))")
    public void logPointcut() {}

    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - startTime;
        
        try {
            saveLog(point, time);
        } catch (Exception e) {
            log.error("保存日志失败", e);
        }
        return result;
    }

    private void saveLog(ProceedingJoinPoint point, long time) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return;
        
        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        
        // 只记录写操作
        if (!"POST".equals(method) && !"PUT".equals(method) && !"DELETE".equals(method)) {
            return;
        }

        SysLog sysLog = new SysLog();
        sysLog.setUserId((Long) request.getAttribute("userId"));
        sysLog.setUsername((String) request.getAttribute("username"));
        sysLog.setOperation(getOperation(request.getRequestURI(), method));
        sysLog.setMethod(point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        sysLog.setParams(JSONUtil.toJsonStr(point.getArgs()));
        sysLog.setIp(getIpAddress(request));
        
        sysLogService.save(sysLog);
        log.info("操作日志: {} - {} - {}ms", sysLog.getOperation(), sysLog.getMethod(), time);
    }

    private String getOperation(String uri, String method) {
        if (uri.contains("/elder")) return "DELETE".equals(method) ? "删除老人" : "POST".equals(method) ? "新增老人" : "更新老人";
        if (uri.contains("/worker")) return "DELETE".equals(method) ? "删除护工" : "POST".equals(method) ? "新增护工" : "更新护工";
        if (uri.contains("/service")) return "DELETE".equals(method) ? "删除服务" : "POST".equals(method) ? "新增服务" : "更新服务";
        if (uri.contains("/order")) return "POST".equals(method) ? "创建订单" : "更新订单";
        if (uri.contains("/health")) return "新增健康记录";
        if (uri.contains("/login")) return "用户登录";
        return "其他操作";
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
}
