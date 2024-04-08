package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.Message;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_message】的数据库操作Mapper
* @createDate 2024-04-08 20:18:06
* @Entity com.fiee.legaladvice.Message
*/
public interface MessageMapper extends BaseMapper<Message> {
    List<Message> getBackMessage(@Param("current") Long current,@Param("size") Long size, @Param("vo") ConditionVO vo);
}




