package com.fiee.legaladvice.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Fiee
 * @ClassName: TagVO
 * @Date: 2024/4/5
 * @Version: v1.0.0
 **/
@Data
public class TagVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
