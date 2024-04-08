package com.fiee.legaladvice.service;

import com.fiee.legaladvice.entity.WebsiteConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.vo.WebsiteConfigVO;

/**
* @author Fiee
* @description 针对表【tb_website_config】的数据库操作Service
* @createDate 2024-04-08 22:35:12
*/
public interface WebsiteConfigService extends IService<WebsiteConfig> {
    WebsiteConfigVO getWebsiteConfig();

    void saveWebsiteConfig(WebsiteConfigVO vo);
}
