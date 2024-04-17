package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Fiee
* @description 针对表【tb_chat_record】的数据库操作Mapper
* @createDate 2024-04-15 22:32:30
* @Entity com.fiee.legaladvice.ChatRecord
*/
@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {

}




