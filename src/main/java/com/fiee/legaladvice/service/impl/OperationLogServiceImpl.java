package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.OperationLog;
import com.fiee.legaladvice.service.OperationLogService;
import com.fiee.legaladvice.mapper.OperationLogMapper;
import org.springframework.stereotype.Service;

/**
* @author Fiee
* @description 针对表【tb_operation_log】的数据库操作Service实现
* @createDate 2024-03-02 01:50:07
*/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService{

}




