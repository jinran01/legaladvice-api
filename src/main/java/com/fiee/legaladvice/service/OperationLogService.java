package com.fiee.legaladvice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.OperationLog;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_operation_log】的数据库操作Service
* @createDate 2024-03-02 01:50:07
*/
public interface OperationLogService extends IService<OperationLog> {

    PageResult<OperationLog> getOperations(ConditionVO conditionVO);

    boolean delOperations(List<Integer> ids);
}
