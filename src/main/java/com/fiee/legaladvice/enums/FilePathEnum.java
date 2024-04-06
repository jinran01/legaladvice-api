package com.fiee.legaladvice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Fiee
 * @ClassName: FilePathEnum
 * @Date: 2024/4/5
 * @Version: v1.0.0
 **/
@Getter
@AllArgsConstructor
public enum FilePathEnum {

    /**
     * 头像路径
     */
    AVATAR("avatar/","头像路径"),

    /**
     * 网站配置图片路径
     */
    CONFIG("config/","配置图片路径"),

    /**
     * 页面图片路径
     */
    PAGES("pages/","页面图片路径"),

    /**
     * 相册封面
     */
    ALBUM("album/","相册封面路径"),

    /**
     * 相片
     */
    PHOTO("photo/","相片路径"),

    /**
     * 文章封面
     */
    ARTICLE_COVER("articles/","文章封面路径"),

    /**
     * 说说图片
     */
    TALK_IMAGES("talks/","说说图片");


    private String path;

    private String desc;
}
