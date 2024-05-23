package com.fiee.legaladvice.strategy.context;

import com.fiee.legaladvice.dto.ArticleSearchDTO;
import com.fiee.legaladvice.strategy.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.fiee.legaladvice.enums.SearchModeEnum.getStrategy;

/**
 * @Author: Fiee
 * @ClassName: SearchStrategyContext
 * @Date: 2024/5/17
 * @Version: v1.0.0
 **/
@Service
public class SearchStrategyContext {
    /**
     * 搜索模式
     */
    @Value("${search.mode}")
    private String searchMode;

    @Autowired
    private Map<String, SearchStrategy> searchStrategyMap;

    /**
     * 执行搜索策略
     *
     * @param keywords 关键字
     * @return {@link List <ArticleSearchDTO>} 搜索文章
     */
    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        return searchStrategyMap.get(getStrategy(searchMode)).searchArticle(keywords);
    }
}
