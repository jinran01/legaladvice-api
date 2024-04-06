package com.fiee.legaladvice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Fiee
 * @ClassName: ArticleStatusEnum
 * @Date: 2023/4/1
 * @Version: v1.0.0
 **/
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {

    PUBLIC(1,"公开"),

    PRIVATE(2,"私密"),

    DRAFT(3,"草稿");
    /**
     * 文章状态
     */
    private final Integer status;
    /**
     * 描述
     */
    private final String desc;
}
