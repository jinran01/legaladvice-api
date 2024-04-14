package com.fiee.legaladvice.mapper;

import com.fiee.legaladvice.dto.CommentDTO;
import com.fiee.legaladvice.dto.ReplyCountDTO;
import com.fiee.legaladvice.dto.ReplyDTO;
import com.fiee.legaladvice.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.vo.CommentVO;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_comment】的数据库操作Mapper
* @createDate 2024-04-08 20:10:56
* @Entity com.fiee.legaladvice.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> getCommentList(@Param("vo") ConditionVO vo, @Param("current") Long current,@Param("size")  Long size);

    Integer count(@Param("vo") ConditionVO vo);
    /**
     * 查看评论
     *
     * @param current   当前页码
     * @param size      大小
     * @param commentVO 评论信息
     * @return 评论集合
     */
    List<CommentDTO> listComments(@Param("current") Long current, @Param("size") Long size, @Param("commentVO") CommentVO commentVO);


    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 查看当条评论下的回复
     *
     * @param commentId 评论id
     * @param current   当前页码
     * @param size      大小
     * @return 回复集合
     */
    List<ReplyDTO> listRepliesByCommentId(@Param("current") Long current, @Param("size") Long size, @Param("commentId") Integer commentId);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);
}




