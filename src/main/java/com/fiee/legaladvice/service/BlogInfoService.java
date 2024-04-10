package com.fiee.legaladvice.service;

import com.fiee.legaladvice.dto.BlogBackInfoDTO;
import com.fiee.legaladvice.dto.BlogHomeInfoDTO;
import com.fiee.legaladvice.vo.WebsiteConfigVO;

/**
 * @Author: Fiee
 * @ClassName: blogInfoService
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
public interface BlogInfoService {
    /**
     * 上传访客信息
     */
    void report();

    /**
     * 获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();

    /**
     * 获取后台首页数据
     * @return 博客后台信息
     */
    BlogBackInfoDTO getBlogBackInfo();

    /**
     * 获取首页数据
     *
     * @return 博客首页信息
     */
    BlogHomeInfoDTO getBlogHomeInfo();

}
