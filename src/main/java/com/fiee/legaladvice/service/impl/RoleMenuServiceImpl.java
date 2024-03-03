package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.RoleMenu;
import com.fiee.legaladvice.service.RoleMenuService;
import com.fiee.legaladvice.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_role_menu】的数据库操作Service实现
* @createDate 2024-03-02 20:03:36
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{
    @Override
    public List<RoleMenu> getRoleMenus(Integer id) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        return baseMapper.selectList(wrapper);
    }
}




