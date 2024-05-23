package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Fiee
 * @ClassName: ArticleSearchDTO
 * @Date: 2024/5/17
 * @Version: v1.0.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDTO {
    /**
     * 文章id
     */
//    @Id
    private Integer id;

    /**
     * 文章标题
     */
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleTitle;

    /**
     * 文章内容
     */
//    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String articleContent;

    /**
     * 是否删除
     */
//    @Field(type = FieldType.Integer)
    private Integer isDelete;

    /**
     * 文章状态
     */
//    @Field(type = FieldType.Integer)
    private Integer status;
}
