package com.fiee.legaladvice.service;

import com.fiee.legaladvice.dto.BlogBackInfoDTO;

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
     * 获取后台首页数据
     * @return 博客后台信息
     */
    BlogBackInfoDTO getBlogBackInfo();
}
