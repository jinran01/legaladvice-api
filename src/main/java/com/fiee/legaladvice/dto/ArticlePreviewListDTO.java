package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: ArticlePreviewListDTO
 * @Date: 2024/4/21
 * @Version: v1.0.0
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePreviewListDTO {
    /**
     * 文章列表
     */
    private List<ArticlePreviewDTO> articlePreviewDTOList;

    /**
     * 条件名
     */
    private String name;
}
