package com.fiee.legaladvice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.OperationLog;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_operation_log】的数据库操作Mapper
* @createDate 2024-03-02 01:50:07
* @Entity com.fiee.legaladvice.OperationLog
*/
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
    List<OperationLog> getOperations(@Param("current") Long current, @Param("size") Long size, @Param("vo") ConditionVO conditionVo);
}




