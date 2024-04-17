package com.fiee.legaladvice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Fiee
 * @ClassName: EmailEnum
 * @Date: 2024/3/3
 * @Version: v1.0.0
 **/
@Getter
@AllArgsConstructor
public enum EmailEnum {
    /**
     * 发送注册\修改邮箱验证码
     */
    COMMON_CODE("验证码","common.html");


    private final String subject;

    private final String template;
}
