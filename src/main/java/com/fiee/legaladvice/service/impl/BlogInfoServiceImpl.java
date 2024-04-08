package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fiee.legaladvice.dto.*;
import com.fiee.legaladvice.entity.Article;
import com.fiee.legaladvice.entity.IncreateData;
import com.fiee.legaladvice.mapper.CategoryMapper;
import com.fiee.legaladvice.service.*;
import com.fiee.legaladvice.utils.BeanCopyUtils;
import com.fiee.legaladvice.utils.IpUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

import static com.fiee.legaladvice.constant.CommonConst.*;
import static com.fiee.legaladvice.constant.RedisPrefixConst.*;

/**
 * @Author: Fiee
 * @ClassName: blogInfoServiceImpl
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Service
public class BlogInfoServiceImpl implements BlogInfoService {
    @Autowired
    private RedisService redisService;
    @Resource
    private HttpServletRequest request;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private  MessageService messageService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private  TagService tagService;
    @Autowired
    private  IncreateDataService increateDataService;

    @Override
    public void report() {
        // 获取ip
        String ipAddress = IpUtils.getIpAddress(request);
        // 获取访问设备
        UserAgent userAgent = IpUtils.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        // 生成唯一用户标识
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisService.sIsMember(UNIQUE_VISITOR, md5)) {
            // 统计游客地域分布
            String ipSource = IpUtils.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                ipSource = ipSource.substring(0, 2)
                        .replaceAll(PROVINCE, "")
                        .replaceAll(CITY, "");
                redisService.hIncr(VISITOR_AREA, ipSource, 1L);
            } else {
                redisService.hIncr(VISITOR_AREA, UNKNOWN, 1L);
            }
            // 访问量+1
            redisService.incr(BLOG_VIEWS_COUNT, 1);
            // 保存唯一标识
            redisService.sAdd(UNIQUE_VISITOR, md5);
        }
    }

    @Override
    public BlogBackInfoDTO getBlogBackInfo() {

        // 查询访问量
        Object count = redisService.get(BLOG_VIEWS_COUNT);
        Integer viewsCount = Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        // 查询留言量
        Integer messageCount = Math.toIntExact(messageService.count(null));
        // 查询用户量
        Integer userCount = Math.toIntExact(userInfoService.count(null));
        // 查询文章量
        Integer articleCount = Math.toIntExact(articleService.count(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, FALSE)));
        // 分类下文章数
        List<CategoryDTO> categoryDTOList = categoryMapper.listCategoryDTO();
        // 查询标签
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagService.list(),TagDTO.class);
        //一周数据量
        List<IncreateData> list = increateDataService.list();
        IncreateDataDTO increateDataDTOS;
        if (list.size() == 0){
            increateDataDTOS = null;
        }else if (list.size() >0 && list.size() <= 7){
            increateDataDTOS = convertWeekData(list);
        }
        else {
            increateDataDTOS = convertWeekData(list.subList(list.size() - 7, list.size() - 1));
        }
        //文章浏览量TOP10
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(ARTICLE_VIEWS_COUNT, 0, 4);

        BlogBackInfoDTO blogBackInfoDTO = BlogBackInfoDTO.builder()
                .viewsCount(viewsCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryDTOList(categoryDTOList)
                .increateDataDTOList(increateDataDTOS)
                .tagDTOList(tagDTOList)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            // 查询文章排行
            List<ArticleRankDTO> articleRankDTOList = listArticleRank(articleMap);
            blogBackInfoDTO.setArticleRankDTOList(articleRankDTOList);
        }
        return blogBackInfoDTO;
    }

    /**
     * 查询文章TOP10
     *
     * @param articleMap 文章信息
     * @return {@link List<ArticleRankDTO>} 文章TOP10
     */
    private List<ArticleRankDTO> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        return articleService.list(new LambdaQueryWrapper<Article>()
                        .select(Article::getId, Article::getArticleTitle)
                        .in(Article::getId, articleIdList))
                .stream().map(article -> ArticleRankDTO.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewsCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankDTO::getViewsCount).reversed())
                .collect(Collectors.toList());
    }


    /**
     * 封装一周数据量
     * @param list
     * @return
     */
    public IncreateDataDTO  convertWeekData(List<IncreateData> list){
        List<Integer> viewWeek = new ArrayList<>();
        List<Integer> articleWeek = new ArrayList<>();
        List<Integer> messageWeek = new ArrayList<>();
        List<Integer> userWeek = new ArrayList<>();
        for (IncreateData item:list) {
            viewWeek.add(item.getIncPv());
            articleWeek.add(item.getIncArt());
            messageWeek.add(item.getIncMsg());
            userWeek.add(item.getIncUser());
        }
        IncreateDataDTO increateDataDTO = IncreateDataDTO.builder()
                .articleWeek(viewWeek)
                .userWeek(userWeek)
                .viewWeek(viewWeek)
                .messageWeek(messageWeek).build();
        return increateDataDTO;
    }
}
