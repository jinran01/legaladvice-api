package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.dto.ArticleDTO;
import com.fiee.legaladvice.dto.ArticlePreviewListDTO;
import com.fiee.legaladvice.entity.Article;
import com.fiee.legaladvice.service.ArticleService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ArticleVO;
import com.fiee.legaladvice.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.fiee.legaladvice.constant.OptTypeConst.*;
import static com.fiee.legaladvice.enums.FilePathEnum.ALBUM;
import static com.fiee.legaladvice.enums.FilePathEnum.ARTICLE_COVER;

/**
 * @Author: Fiee
 * @ClassName: ArticleController
 * @Date: 2023/4/11
 * @Version: v1.0.0
 **/
@Api(tags = "文章管理")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation("添加或修改文章")
    @PostMapping("/admin/articles")
    public Result saveArticle(@RequestBody ArticleVO articleVO){
        return Result.ok(articleService.saveOrUpdateArticle(articleVO));
    }
    @OptLog(optType = UPDATE)
    @ApiOperation("修改文章置顶")
    @PutMapping("/admin/articles/top")
    public Result updateIsTop(@RequestBody Article article){
        return Result.ok(articleService.updateById(article));
    }
    @OptLog(optType = UPDATE)
    @ApiOperation("恢复或删除文章")
    @PutMapping("/admin/articles")
    public Result reviewOrRemove(@RequestBody Article[] articles){
        return Result.ok(articleService.updateBatchById(Arrays.asList(articles)));
    }
    @OptLog(optType = REMOVE)
    @ApiOperation("物理删除文章")
    @DeleteMapping("/admin/articles")
    public Result deleteArticle(@RequestBody Long[] articleIds){
        return Result.ok(articleService.deleteArticle(articleIds));
    }
    @ApiOperation("查看后台文章")
    @GetMapping("/admin/articles")
    public Result getArticleById(ConditionVO vo){
        return Result.ok(articleService.getBackArticle(vo));
    }
    @ApiOperation("根据id查看后台文章")
    @GetMapping("/admin/articles/{id}")
    public Result getArticleBackById(@PathVariable Integer id){
        return Result.ok(articleService.getArticleBackById(id));
    }
    /**
     * 导出文章
     *
     * @param articleIdList 文章id列表
     * @return {@link List <String>} 文件url列表
     */
    @ApiOperation(value = "导出文章")
    @ApiImplicitParam(name = "articleIdList", value = "文章id", required = true, dataType = "List<Integer>")
    @PostMapping("/admin/articles/export")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIdList) {
        return Result.ok(articleService.exportArticles(articleIdList));
    }


    /**
     * 查看首页文章
     *
     * @return {@link Result} 文章信息
     */
    @ApiOperation("查看首页文章")
    @GetMapping("/articles")
    public Result getHomeArticles(ConditionVO vo){
        return Result.ok(articleService.getHomeArticles(vo));
    }

    /**
     * 根据条件查询文章
     *
     * @param condition 条件
     * @return {@link Result<ArticlePreviewListDTO>} 文章列表
     */
    @ApiOperation(value = "根据条件查询文章")
    @GetMapping("/articles/condition")
    public Result<ArticlePreviewListDTO> listArticlesByCondition(ConditionVO condition) {
        return Result.ok(articleService.listArticlesByCondition(condition));
    }
    /**
     * 根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link Result<ArticleDTO>} 文章信息
     */
    @ApiOperation(value = "根据id查看文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/articles/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleById(articleId));
    }
    /**
     * 点赞文章
     *
     * @param articleId 文章id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "点赞文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @PostMapping("/articles/{articleId}/like")
    public Result<?> saveArticleLike(@PathVariable("articleId") Integer articleId) {
        articleService.saveArticleLike(articleId);
        return Result.ok();
    }

    /**
     * 文章访问量top10
     * @return {@link Result<>}
     */
    @ApiOperation(value = "文章访问量top10")
    @GetMapping("/articles/top")
    public Result<?> getTopArticles() {
        return Result.ok(articleService.getTopArticles());
    }
}
