package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fiee
 * @ClassName: LawyerTopDTO
 * @Date: 2024/4/30
 * @Version: v1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LawyerTopDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userAuthId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;
}
