package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.Comment;
import com.fiee.legaladvice.service.CommentService;
import com.fiee.legaladvice.mapper.CommentMapper;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_comment】的数据库操作Service实现
* @createDate 2024-04-08 20:10:56
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{
    @Override
    public PageResult getCommentList(ConditionVO vo) {

        Integer count = baseMapper.count(vo);

        List<Comment> commentList = baseMapper.getCommentList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());

        return new PageResult<>(commentList,count);
    }
}




