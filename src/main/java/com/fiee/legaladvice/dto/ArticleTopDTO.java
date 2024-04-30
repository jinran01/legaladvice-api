package com.fiee.legaladvice.dto;

import com.fiee.legaladvice.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: ArticleTopDTO
 * @Date: 2024/4/30
 * @Version: v1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleTopDTO {
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 文章title
     */
    private String articleTitle;
    /**
     * 文章分类
     */
    private String categoryName;
    /**
     * 文章标签列表
     */
    private List<String> tagNameList;
    /**
     * 文章发表时间
     */
    private LocalDateTime createTime;
    /**
     * 文章访问量
     */
    private Double viewCount;
}
