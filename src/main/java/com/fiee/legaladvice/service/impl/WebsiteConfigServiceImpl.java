package com.fiee.legaladvice.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.IncreateData;
import com.fiee.legaladvice.entity.UniqueView;
import com.fiee.legaladvice.entity.WebsiteConfig;
import com.fiee.legaladvice.service.*;
import com.fiee.legaladvice.mapper.WebsiteConfigMapper;
import com.fiee.legaladvice.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.fiee.legaladvice.constant.RedisPrefixConst.*;

/**
 * @author Fiee
 * @description 针对表【tb_website_config】的数据库操作Service实现
 * @createDate 2024-04-08 22:35:12
 */
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig>
        implements WebsiteConfigService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UniqueViewService uniqueViewService;
    @Autowired
    private IncreateDataService increateDataService;

    /**
     * 获取博客配置信息
     *
     * @return
     */
    @Override
    public WebsiteConfigVO getWebsiteConfig() {
        WebsiteConfigVO websiteConfigVO;
        Object webConfig = redisService.get(WEBSITE_CONFIG);
        if (!Objects.isNull(webConfig)) {
            //从redis获取数据
            websiteConfigVO = JSON.parseObject(webConfig.toString(), WebsiteConfigVO.class);
        } else {
            WebsiteConfig config = baseMapper.selectById(1);
            String websiteConfig = config.getConfig();
            websiteConfigVO = JSON.parseObject(websiteConfig, WebsiteConfigVO.class);
            redisService.set(WEBSITE_CONFIG, websiteConfig);
        }
        return websiteConfigVO;
    }


    /**
     * 保存网站配置信息
     */
    @Override
    public void saveWebsiteConfig(WebsiteConfigVO vo) {
        //更新操作
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(1)
                .config(JSON.toJSONString(vo))
                .build();
        this.updateById(websiteConfig);
        //设置redis缓存
        redisService.set(WEBSITE_CONFIG, JSON.toJSONString(vo));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void savaIncreateData() {
        //获取前天日期
        LocalDateTime localDateTime = LocalDateTimeUtil.offset(LocalDateTime.now(ZoneId.of("Asia/Shanghai")), -1, ChronoUnit.DAYS);

        Integer lastIncPv = Math.toIntExact(redisService.sSize(UNIQUE_VISITOR));
        Integer lastIncArt = Objects.isNull(redisService.get(INCREATE_ART)) ? 0 :(Integer) redisService.get(INCREATE_ART);
        Integer lastIncUser = Objects.isNull(redisService.get(INCREATE_USER)) ? 0 : (Integer) redisService.get(INCREATE_USER);
        Integer lastIncMsg = Objects.isNull(redisService.get(INCREATE_MSG)) ? 0 :(Integer) redisService.get(INCREATE_MSG);

        IncreateData increateData = IncreateData.builder()
                .dateTime(localDateTime)
                .incArt(lastIncArt)
                .incPv(lastIncPv)
                .incUser(lastIncUser)
                .incMsg(lastIncMsg)
                .build();
        UniqueView uniqueView = UniqueView.builder()
                .viewsCount(lastIncPv)
                .createTime(localDateTime)
                .build();

        increateDataService.save(increateData);
        uniqueViewService.save(uniqueView);
    }
    @Scheduled(cron = "1 0 0 * * ?")
    public void clearIncreateData() {
        //清空昨天增量数据
        redisService.del(UNIQUE_VISITOR);
        redisService.del(INCREATE_ART);
        redisService.del(INCREATE_USER);
        redisService.del(INCREATE_MSG);
    }
}




