package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.OperationLog;
import com.fiee.legaladvice.service.OperationLogService;
import com.fiee.legaladvice.mapper.OperationLogMapper;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_operation_log】的数据库操作Service实现
* @createDate 2024-03-02 01:50:07
*/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService{

    @Override
    public PageResult<OperationLog> getOperations(ConditionVO vo) {

        LambdaQueryWrapper<OperationLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!StringUtils.isBlank(vo.getKeywords()),OperationLog::getOptDesc,vo.getKeywords());
        wrapper.like(!StringUtils.isBlank(vo.getKeywords()),OperationLog::getOptModule,vo.getKeywords());
        //日志条数
        Integer count = Math.toIntExact(this.count(wrapper));
        List operations = baseMapper.getOperations(
                (vo.getCurrent() - 1) * vo.getSize(),
                vo.getSize(), vo);
        return new PageResult<>(operations,count);
    }

    @Override
    public boolean delOperations(List<Integer> ids) {
        return this.removeBatchByIds(ids);
    }
}




