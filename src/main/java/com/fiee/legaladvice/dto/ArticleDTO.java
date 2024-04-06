package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 文章
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    /**
     * id
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
     * 内容
     */
    private String articleContent;


    /**
     * 文章类型
     */
    private Integer type;

    /**
     * 原文链接
     */
    private String originalUrl;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;


    /**
     * 文章分类名
     */
    private String categoryName;

    /**
     * 文章标签
     */
    private List<TagDTO> tagDTOList;

//    /**
//     * 上一篇文章
//     */
//    private ArticlePaginationDTO lastArticle;
//
//    /**
//     * 下一篇文章
//     */
//    private ArticlePaginationDTO nextArticle;
//
//    /**
//     * 推荐文章列表
//     */
//    private List<ArticleRecommendDTO> recommendArticleList;
//
//    /**
//     * 最新文章列表
//     */
//    private List<ArticleRecommendDTO> newestArticleList;

}
