package com.fiee.legaladvice.dto;

import com.fiee.legaladvice.vo.WebsiteConfigVO;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.dto
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Data
@Builder
public class BlogInfoDTO {
    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;

    /**
     * 访问量
     */
    private Integer viewsCount;

    /**
     * 网站配置
     */
    private WebsiteConfigVO websiteConfig;

    /**
     * 页面列表
     */
//    private List<Page> pageList;
}
