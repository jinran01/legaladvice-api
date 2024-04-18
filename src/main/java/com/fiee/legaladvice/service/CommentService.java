package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.dto.CommentDTO;
import com.fiee.legaladvice.dto.ReplyDTO;
import com.fiee.legaladvice.entity.Comment;
import com.fiee.legaladvice.vo.CommentVO;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_comment】的数据库操作Service
* @createDate 2024-04-08 20:10:56
*/
public interface CommentService extends IService<Comment> {
    PageResult getCommentList(ConditionVO vo);


    PageResult<CommentDTO> listComments(CommentVO vo);

    void saveCommentLike(Integer commentId);

    void saveComment(CommentVO commentVO);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);
}
