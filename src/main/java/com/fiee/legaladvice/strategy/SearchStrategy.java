package com.fiee.legaladvice.strategy;

import com.fiee.legaladvice.dto.ArticleSearchDTO;

import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: SearchStrategy
 * @Date: 2024/5/17
 * @Version: v1.0.0
 **/
public interface SearchStrategy {
    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return {@link List<ArticleSearchDTO>} 文章列表
     */
    List<ArticleSearchDTO> searchArticle(String keywords);
}
