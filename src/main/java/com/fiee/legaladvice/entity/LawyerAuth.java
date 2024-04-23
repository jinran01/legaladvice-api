package com.fiee.legaladvice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Fiee
 * @ClassName: LawyerAuth
 * @Date: 2024/4/23
 * @Version: v1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="tb_lawyer_auth")
public class LawyerAuth implements Serializable {

    /**
     *id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户infoId
     */
    private Integer userInfoId;

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
     * 身份证号码
     */
    private String idCard;

    /**
     * 身份证认证
     */
    private String idCardAuth;

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
     * 律师执业证书
     */
    private String lpc;

    /**
     * 法律职业资格证书
     */
    private String lpqc;

    /**
     * 状态(1:通过2:审核中3:未通过)
     */
    private Integer status;
    /**
     * 创建时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 用于备注审核通不通过时所填信息
     */
    private String remarks;
}
