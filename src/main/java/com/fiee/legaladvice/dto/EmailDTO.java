package com.fiee.legaladvice.dto;


import lombok.Builder;
import lombok.Data;


/**
 * @Author: Fiee
 * @ClassName: EmailDTO
 * @Date: 2024/3/3
 * @Version: v1.0.0
 **/
@Builder
@Data
public class EmailDTO {

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 主题
     */
    private String subject;

    /**
     * 邮箱模板
     */
    private String template;

}
