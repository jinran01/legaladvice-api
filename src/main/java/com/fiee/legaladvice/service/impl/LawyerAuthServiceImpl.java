package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiee.legaladvice.entity.LawyerAuth;
import com.fiee.legaladvice.mapper.LawyerAuthMapper;
import com.fiee.legaladvice.service.LawyerAuthService;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Fiee
 * @ClassName: LawyerAuthServiceImpl
 * @Date: 2024/4/23
 * @Version: v1.0.0
 **/
@Service
public class LawyerAuthServiceImpl extends ServiceImpl<LawyerAuthMapper, LawyerAuth>
        implements LawyerAuthService {
    @Override
    public PageResult<LawyerAuth> getLawyerList(ConditionVO vo) {
        List<LawyerAuth> list = baseMapper.getLawyerList(vo, (vo.getCurrent() - 1) * vo.getSize(), vo.getSize());
        Integer count = Math.toIntExact(this.count());
        return new PageResult<>(list,count);
    }
}
