package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fiee
 * @ClassName: ConsultUserDTO 聊天对象列表
 * @Date: 2024/4/27
 * @Version: v1.0.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultUserDTO {
    /**
     * 聊天用户ID
     */
    private Integer userAuthId;
    /**
     * 聊天用户头像
     */
    private String avatar;
    /**
     * 聊天用户昵称
     */
    private String nickname;
    /**
     * 聊天用户姓名
     */
    private String name;
}
