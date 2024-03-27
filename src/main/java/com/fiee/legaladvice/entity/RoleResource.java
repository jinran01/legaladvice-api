package com.fiee.legaladvice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.entity
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Data
@TableName("tb_role_resource")
public class RoleResource implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer resourceId;

    private static final long serialVersionUID = 1L;


}
