package com.fiee.legaladvice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Fiee
 * @ClassName: MessageMapper
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
