package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: ArticlePreviewDTO
 * @Date: 2024/4/21
 * @Version: v1.0.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePreviewDTO {
    /**
     * 文章id
     */
    private Integer id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private Date createTime;

    /**
     * 文章分类id
     */
    private Integer categoryId;

    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;
}
