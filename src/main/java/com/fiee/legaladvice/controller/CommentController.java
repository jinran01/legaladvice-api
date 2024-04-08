package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.entity.Comment;
import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.service.CommentService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation("查询后台评论")
    @GetMapping("/comments")
    public Result searchCategory(ConditionVO vo){
        return Result.ok(commentService.getCommentList(vo));
    }

    @OptLog(optType = REMOVE)
    @ApiOperation("删除评论")
    @DeleteMapping("/comments")
    public Result deleteComment(@RequestBody Long[] ids){
        return Result.ok(commentService.removeBatchByIds(Arrays.asList(ids)));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("审核评论")
    @PutMapping("/comments/review")
    public Result reviewComment(@RequestBody Comment[] comment){
        return Result.ok(commentService.updateBatchById(Arrays.asList(comment)));
    }
}