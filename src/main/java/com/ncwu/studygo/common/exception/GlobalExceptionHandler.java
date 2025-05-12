package com.ncwu.studygo.common.exception;

import com.ncwu.studygo.common.result.Result;
import com.ncwu.studygo.common.result.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public Result<String> handleValidException(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        }
        
        StringBuilder message = new StringBuilder();
        if (bindingResult != null && bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message.append(fieldError.getField()).append(fieldError.getDefaultMessage());
            }
        } else {
            message.append("参数验证失败");
        }
        
        return Result.failed(ResultCode.VALIDATE_FAILED.getCode(), message.toString());
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(value = BusinessException.class)
    public Result<String> handleBusinessException(BusinessException e) {
        return Result.failed(e.getCode(), e.getMessage());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.failed("系统异常：" + e.getMessage());
    }
}