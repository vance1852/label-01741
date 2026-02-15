package com.elderly.config;

import com.elderly.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 统一返回 ResponseEntity，HTTP状态码与业务码保持一致
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError() != null 
                ? e.getBindingResult().getFieldError().getDefaultMessage() 
                : "参数校验失败";
        log.warn("参数校验失败: {}", message);
        return ResponseEntity.badRequest().body(Result.badRequest(message));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Result<?>> handleBindException(BindException e) {
        String message = e.getFieldError() != null 
                ? e.getFieldError().getDefaultMessage() 
                : "参数绑定失败";
        log.warn("参数绑定失败: {}", message);
        return ResponseEntity.badRequest().body(Result.badRequest(message));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("非法参数: {}", e.getMessage());
        return ResponseEntity.badRequest().body(Result.badRequest(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<?>> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(Exception e) {
        log.error("系统异常: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("系统繁忙，请稍后重试"));
    }
}
