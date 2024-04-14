package com.fiee.legaladvice.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.dto.*;
import com.fiee.legaladvice.entity.Article;
import com.fiee.legaladvice.entity.ArticleTag;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.entity.Tag;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.mapper.TagMapper;
import com.fiee.legaladvice.service.*;
import com.fiee.legaladvice.mapper.ArticleMapper;
import com.fiee.legaladvice.utils.BeanCopyUtils;
import com.fiee.legaladvice.utils.CommonUtils;
import com.fiee.legaladvice.utils.UserUtils;
import com.fiee.legaladvice.vo.ArticleVO;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.fiee.legaladvice.constant.CommonConst.ARTICLE_SET;
import static com.fiee.legaladvice.constant.CommonConst.FALSE;
import static com.fiee.legaladvice.constant.RedisPrefixConst.ARTICLE_LIKE_COUNT;
import static com.fiee.legaladvice.constant.RedisPrefixConst.ARTICLE_VIEWS_COUNT;
import static com.fiee.legaladvice.enums.ArticleStatusEnum.DRAFT;
import static com.fiee.legaladvice.enums.ArticleStatusEnum.PUBLIC;

/**
 * @author Fiee
 * @description 针对表【tb_article】的数据库操作Service实现
 * @createDate 2023-04-11 16:48:23
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Autowired
    private HttpSession session;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagService tagService;
    @Autowired
    private RedisService redisService;

    /**
     * 首页文章
     * @return
     */
    @Override
    public List<ArticleHomeDTO> getHomeArticles(ConditionVO vo) {
        return baseMapper.homeArticleList(vo,(vo.getCurrent()-1)*vo.getSize());
    }

    /**
     * 新增或者更新文章
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateArticle(ArticleVO vo) {
        //保存分类
        Category category = saveArticleCategory(vo);
        //保存或者修改文章
        Article article = BeanCopyUtils.copyObject(vo, Article.class);
        //设置分类id
        if (Objects.nonNull(category)) {
            article.setCategoryId(category.getId());
        }
        //设置发表文章用户id
        article.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(article);
        //保存标签
        saveArticleTag(vo, article.getId());
        return true;
    }

    /**
     * 根据id查看后台文章
     * @param articleId
     * @return
     */
    @Override
    public ArticleVO getArticleBackById(Integer articleId) {
        // 查询文章信息
        Article article = baseMapper.selectById(articleId);
        // 查询文章分类
        Category category = categoryService.getById(article.getCategoryId());
        String categoryName = null;
        if (Objects.nonNull(category)) {
            categoryName = category.getCategoryName();
        }
        // 查询文章标签
        List<String> tagNameList = tagMapper.listTagNameByArticleId(articleId);
        // 封装数据
        ArticleVO articleVO = BeanCopyUtils.copyObject(article, ArticleVO.class);
        articleVO.setCategoryName(categoryName);
        articleVO.setTagNameList(tagNameList);
        return articleVO;
    }

    /**
     * 保存标签
     *
     * @param vo
     * @param articleId
     */
    private void saveArticleTag(ArticleVO vo, Integer articleId) {
        //如果是编辑文章 则删除所有该文章绑定的标签
        if (Objects.nonNull(vo.getId())) {
            LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTag::getArticleId, vo.getId());
            articleTagService.remove(wrapper);
        }
        List<String> tagNameList = vo.getTagNameList();
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Tag::getTagName, vo.getTagNameList());

            //已存在的标签
            List<Tag> existTagList = tagService.list(wrapper);
            List<String> existTagNameList = existTagList.stream().map(Tag::getTagName).collect(Collectors.toList());
            List<Integer> existTagIdList = existTagList.stream().map(Tag::getId).collect(Collectors.toList());

            //对比新增不存在的标签
            tagNameList.removeAll(existTagNameList);

            if (CollectionUtils.isNotEmpty(tagNameList)) {
                List<Tag> tagList = tagNameList.stream().map(
                        item -> Tag.builder().tagName(item).build()
                ).collect(Collectors.toList());
                //新增的标签
                tagService.saveBatch(tagList);
                //收集tagId
                List<Integer> tagIdList = tagList.stream().map(Tag::getId).collect(Collectors.toList());
                existTagIdList.addAll(tagIdList);
            }

            //绑定标签
            List<ArticleTag> articleTagList = existTagIdList.stream()
                    .map(item -> ArticleTag.builder().tagId(item).articleId(articleId).build())
                    .collect(Collectors.toList());
            articleTagService.saveBatch(articleTagList);
        }
    }

    /**
     * 保存分类
     *
     * @param vo
     * @return
     */
    private Category saveArticleCategory(ArticleVO vo) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getCategoryName, vo.getCategoryName());
        Category category = categoryService.getOne(wrapper);
        //判断该分类是否存在 存在返回不存在则新增
        if (Objects.isNull(category) && !vo.getStatus().equals(DRAFT.getStatus())) {
            category = Category.builder().categoryName(vo.getCategoryName()).build();
            categoryService.save(category);
        }
        return category;
    }

    /**
     * @param articleId
     * @return
     */
    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // 查询推荐文章
        CompletableFuture<List<ArticleRecommendDTO>> recommendArticleList = CompletableFuture.supplyAsync(() -> baseMapper.listRecommendArticles(articleId));
        // 查询最新文章
        CompletableFuture<List<ArticleRecommendDTO>> newestArticleList = CompletableFuture.supplyAsync(() -> {
            List<Article> articleList = this.list(new LambdaQueryWrapper<Article>()
                    .select(Article::getId, Article::getArticleTitle, Article::getArticleCover, Article::getCreateTime).eq(Article::getIsDelete, FALSE)
                    .eq(Article::getStatus, PUBLIC.getStatus()).orderByDesc(Article::getId).last("limit 5"));
            return BeanCopyUtils.copyList(articleList, ArticleRecommendDTO.class);
        });
        // 查询id对应文章
        ArticleDTO article = baseMapper.getArticleById(articleId);
        if (Objects.isNull(article)) {
            throw new BizException("文章不存在");
        }
        // 更新文章浏览量
        updateArticleViewsCount(articleId);
        // 查询上一篇下一篇文章
        Article lastArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .lt(Article::getId, articleId)
                .orderByDesc(Article::getId).last("limit 1"));
        Article nextArticle = baseMapper.selectOne(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle, Article::getArticleCover)
                .eq(Article::getIsDelete, FALSE)
                .eq(Article::getStatus, PUBLIC.getStatus())
                .gt(Article::getId, articleId).orderByAsc(Article::getId)
                .last("limit 1"));
        article.setLastArticle(BeanCopyUtils.copyObject(lastArticle, ArticlePaginationDTO.class));
        article.setNextArticle(BeanCopyUtils.copyObject(nextArticle, ArticlePaginationDTO.class));
        // 封装点赞量和浏览量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            article.setViewsCount(score.intValue());
        }
        article.setLikeCount((Integer) redisService.hGet(ARTICLE_LIKE_COUNT, articleId.toString()));
        // 封装文章信息
        try {
            article.setRecommendArticleList(recommendArticleList.get());
            article.setNewestArticleList(newestArticleList.get());
        } catch (Exception e) {
            log.error(StrUtil.format("堆栈信息:{}", ExceptionUtil.stacktraceToString(e)));
        }
        return article;
    }
    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        Set<Integer> articleSet = CommonUtils.castSet(Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElseGet(HashSet::new), Integer.class);
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }
    /**
     * 查询后台文章
     * @param vo
     * @return
     */
    @Override
    public PageResult<ArticleBackDTO> getBackArticle(ConditionVO vo) {
        //查询总条数
        Integer count = baseMapper.getCount(vo);

        List<ArticleBackDTO> articleList =
                baseMapper.backArticleList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());

        return new PageResult<>(articleList,count);
    }

    @Override
    public List<String> exportArticles(List<Integer> articleIdList) {
        // 查询文章信息
//        List<Article> articleList = articleService.selectList(new LambdaQueryWrapper<Article>()
//                .select(Article::getArticleTitle, Article::getArticleContent)
//                .in(Article::getId, articleIdList));
//        // 写入文件并上传
//        List<String> urlList = new ArrayList<>();
//        for (Article article : articleList) {
//            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(article.getArticleContent().getBytes())) {
//                String url = uploadStrategyContext.executeUploadStrategy(article.getArticleTitle() + FileExtEnum.MD.getExtName(), inputStream, FilePathEnum.MD.getPath());
//                urlList.add(url);
//            } catch (Exception e) {
//                log.error(StrUtil.format("导入文章失败,堆栈:{}", ExceptionUtil.stacktraceToString(e)));
//                throw new BizException("导出文章失败");
//            }
//        }
//        return urlList;
        return null;
    }
    /**
     * 物理删除文章
     * @param articleIds 文章ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteArticle(Long[] articleIds) {
        //删除该文章绑定的标签 article_tag 表中的内容
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ArticleTag::getArticleId,articleIds);
        articleTagService.remove(wrapper);
        this.removeByIds(Arrays.asList(articleIds));
        return false;
    }
}




