package com.fiee.legaladvice.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fiee
 * @ClassName: LawyerAuthDTO
 * @Date: 2024/4/26
 * @Version: v1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LawyerAuthDTO {

    /**
     *id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户Id
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
     * 真实头像
     */
    private String avatar;


    /**
     * 学历
     */
    private String qualification;

    /**
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String major;

    /**
     * 状态(1:通过2:审核中3:未通过)
     */
    private Integer status;

    /**
     * 点赞数量
     */
    private Double likeCount;
}
