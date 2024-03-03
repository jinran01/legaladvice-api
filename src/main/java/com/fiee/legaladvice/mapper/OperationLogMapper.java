package com.fiee.legaladvice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Fiee
* @description 针对表【tb_operation_log】的数据库操作Mapper
* @createDate 2024-03-02 01:50:07
* @Entity com.fiee.legaladvice.OperationLog
*/
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}




