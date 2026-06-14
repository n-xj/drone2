package com.md.drone.exception;

import com.md.drone.common.AjaxResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * REST API 全局异常处理。
 * 在控制器中使用
 * 捕获所有控制器中的异常，并返回错误响应
 */
@RestControllerAdvice(basePackages = "com.md.drone.controller")
public class RestExceptionHandler {

    /**
     * @param ex 异常
     * @return 错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)//捕获 IllegalArgumentException 异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)//返回 400 错误响应
    public AjaxResult<Void> handleBadRequest(IllegalArgumentException ex) {//返回错误响应
        return AjaxResult.error(ex.getMessage());//返回错误信息
    }

    /**
     * @param ex 异常
     * @return 错误响应
     */
    @ExceptionHandler(IllegalStateException.class)//捕获 IllegalStateException 异常
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)//返回 503 错误响应
    public AjaxResult<Void> handleIllegalState(IllegalStateException ex) {
        return AjaxResult.error(ex.getMessage());
    }

    /**
     * @param ex 异常
     * @return 错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//返回 400 错误响应
    public AjaxResult<Void> handleValidation(MethodArgumentNotValidException ex) {//捕获 MethodArgumentNotValidException 异常
        String msg = ex.getBindingResult().getFieldErrors().stream()//获取错误信息
                .findFirst()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())//获取字段名和错误信息
                .orElse("参数校验失败");
        return AjaxResult.error(msg);//返回错误信息
    }

    /**
     * @param ex 异常
     * @return 错误响应
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public AjaxResult<Void> handleBind(BindException ex) {//捕获 BindException 异常
        String msg = ex.getBindingResult().getFieldErrors().stream()//获取错误信息
                .findFirst()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())//获取字段名和错误信息
                .orElse("参数校验失败");
        return AjaxResult.error(msg);//返回错误信息
    }
}
