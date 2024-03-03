package com.fiee.legaladvice.exception;

import com.fiee.legaladvice.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.fiee.legaladvice.enums.StatusCodeEnum.FAIL;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.exception
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }


}
