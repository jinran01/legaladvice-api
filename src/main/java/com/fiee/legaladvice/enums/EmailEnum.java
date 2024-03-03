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
     * 发送邮箱验证码
     */
    SEND_CODE("验证码","MailCode.html");


    private final String subject;

    private final String template;
}
