package com.fiee.legaladvice.mapper;

import com.fiee.legaladvice.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}




