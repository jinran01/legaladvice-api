package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.dto.CommentDTO;
import com.fiee.legaladvice.entity.Comment;
import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.service.CommentService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.CommentVO;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

import static com.fiee.legaladvice.constant.OptTypeConst.*;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Api(tags = "评论管理")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "查询评论")
    @GetMapping("/comments")
    public Result<PageResult<CommentDTO>> listComments(CommentVO commentVO) {
        return Result.ok(commentService.listComments(commentVO));
    }
    /**
     * 添加评论
     *
     * @param commentVO 评论信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("/comments")
    public Result<?> saveComment(@Valid @RequestBody CommentVO commentVO) {
//        commentService.saveComment(commentVO);
        return Result.ok();
    }
    /**
     * 评论点赞
     *
     * @param commentId 评论id
     * @return {@link Result<>}
     */
    @ApiOperation(value = "评论点赞")
    @PostMapping("/comments/{commentId}/like")
    public Result<?> saveCommentLike(@PathVariable("commentId") Integer commentId) {
        commentService.saveCommentLike(commentId);
        return Result.ok();
    }


    @ApiOperation("查询后台评论")
    @GetMapping("/admin/comments")
    public Result searchCategory(ConditionVO vo){
        return Result.ok(commentService.getCommentList(vo));
    }

    @OptLog(optType = REMOVE)
    @ApiOperation("删除评论")
    @DeleteMapping("/admin/comments")
    public Result deleteComment(@RequestBody Long[] ids){
        return Result.ok(commentService.removeBatchByIds(Arrays.asList(ids)));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("审核评论")
    @PutMapping("/admin/comments/review")
    public Result reviewComment(@RequestBody Comment[] comment){
        return Result.ok(commentService.updateBatchById(Arrays.asList(comment)));
    }
}