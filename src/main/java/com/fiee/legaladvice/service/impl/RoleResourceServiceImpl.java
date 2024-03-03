package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.Role;
import com.fiee.legaladvice.entity.RoleResource;
import com.fiee.legaladvice.service.RoleResourceService;
import com.fiee.legaladvice.mapper.RoleResourceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_role_resource】的数据库操作Service实现
* @createDate 2024-03-02 20:03:39
*/
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource>
    implements RoleResourceService{
    @Override
    public List getRoleResource(Role role) {
        LambdaQueryWrapper<RoleResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleResource::getRoleId,role.getId());
        return baseMapper.selectList(wrapper);
    }
}




