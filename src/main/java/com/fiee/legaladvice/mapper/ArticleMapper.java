package com.fiee.legaladvice.mapper;

import com.fiee.legaladvice.dto.ArticleBackDTO;
import com.fiee.legaladvice.dto.ArticleDTO;
import com.fiee.legaladvice.dto.ArticleHomeDTO;
import com.fiee.legaladvice.dto.ArticleRecommendDTO;
import com.fiee.legaladvice.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.vo.ArticleVO;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_article】的数据库操作Mapper
* @createDate 2023-04-11 16:48:23
* @Entity com.fiee.fieeblog.entity.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<ArticleHomeDTO> homeArticleList(@Param("vo") ConditionVO vo,@Param("current") Long current);

    List<ArticleBackDTO> backArticleList(@Param("vo") ConditionVO vo,@Param("current") Long current,@Param("size") Long size);

    List<ArticleRecommendDTO> listRecommendArticles(@Param("articleId") Integer articleId);
    Integer getCount(@Param("vo") ConditionVO vo);

    ArticleDTO getArticleById(Integer id);
}




