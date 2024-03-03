package com.fiee.legaladvice.handle;

import com.fiee.legaladvice.enums.StatusCodeEnum;
import com.fiee.legaladvice.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author: Fiee
 * @ClassName: ExceptionHandler 全局异常处理
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    Result handleException(RuntimeException e){
        e.printStackTrace();
        return Result.fail(StatusCodeEnum.FAIL.getCode(),e.getMessage());
    }

//    @ExceptionHandler(value = Exception.class)
//    Result handleException(Exception e){
//        e.printStackTrace();
//        return Result.fail(StatusCodeEnum.FAIL.getCode(),e.getMessage());
//    }
}
