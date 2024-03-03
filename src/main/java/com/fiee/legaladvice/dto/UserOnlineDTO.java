package com.fiee.legaladvice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Fiee
 * @ClassName: UserOnlineDTO
 * @Date: 2024/3/3
 * @Version: v1.0.0
 **/
@Data
@Builder
public class UserOnlineDTO implements Serializable {
    /**
     * 用户信息Id
     */
    private Integer userInfoId;
    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户登录ip
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;
}
