package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.Message;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_message】的数据库操作Service
* @createDate 2024-04-08 20:18:06
*/

public interface MessageService extends IService<Message> {
    List<Message> getMessage();

    boolean saveMessage(Message message, HttpServletRequest request);

    PageResult<Message> getBackMessage(ConditionVO vo);

    boolean reviewMessages(List<Integer> ids);
}
