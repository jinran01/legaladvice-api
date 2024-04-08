package com.fiee.legaladvice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.WebsiteConfig;
import com.fiee.legaladvice.service.*;
import com.fiee.legaladvice.mapper.WebsiteConfigMapper;
import com.fiee.legaladvice.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.fiee.legaladvice.constant.RedisPrefixConst.WEBSITE_CONFIG;

/**
* @author Fiee
* @description 针对表【tb_website_config】的数据库操作Service实现
* @createDate 2024-04-08 22:35:12
*/
@Service
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigMapper, WebsiteConfig>
    implements WebsiteConfigService{
    @Autowired
    private RedisService redisService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
//    @Autowired
//    private PageService pageService;

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
}




