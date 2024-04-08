package com.fiee.legaladvice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.dto
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentBackDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 被回复用户昵称
     */
    private String replyNickname;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论类型
     */
    private Integer type;

    /**
     * 是否审核
     */
    private Integer isReview;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;


}