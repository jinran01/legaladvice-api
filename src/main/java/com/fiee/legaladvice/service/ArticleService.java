package com.fiee.legaladvice.service;

import com.fiee.legaladvice.dto.ArticleBackDTO;
import com.fiee.legaladvice.dto.ArticleDTO;
import com.fiee.legaladvice.dto.ArticleHomeDTO;
import com.fiee.legaladvice.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.vo.ArticleVO;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_article】的数据库操作Service
* @createDate 2023-04-11 16:48:23
*/
public interface ArticleService extends IService<Article> {

    List<ArticleHomeDTO> getHomeArticles();

    boolean saveOrUpdateArticle(ArticleVO articleVO);
    ArticleVO getArticleBackById(Integer articleId);
    ArticleDTO getArticleById(Integer articleId);

    PageResult<ArticleBackDTO> getBackArticle(ConditionVO vo);

    boolean deleteArticle(Long[] articleIds);
}
