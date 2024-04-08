package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.Message;
import com.fiee.legaladvice.mapper.MessageMapper;
import com.fiee.legaladvice.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * @Author: Fiee
 * @ClassName: MessageServiceImpl
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
}
