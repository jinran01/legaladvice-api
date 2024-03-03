package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.Menu;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.service.MenuService;
import com.fiee.legaladvice.mapper.MenuMapper;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_menu】的数据库操作Service实现
* @createDate 2024-03-03 20:28:22
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{
    @Autowired
    private RedisService redisService;

    /**
     * 获取菜单
     * @return
     */
    @Override
    public List<Menu> getMenuList(String menuName) {
        boolean flag = !StringUtils.isBlank(menuName);
        if (flag){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(true,Menu::getName,menuName);
            List<Menu> list = this.list(wrapper);
            List<Menu> menuList = MenuHelper.buildTree(list);
            return menuList;
        }else {
            if (redisService.get("menuList") != null && redisService.get("menuList") != "") {
                List list = (List) redisService.get("menuList");
                return list;
            } else {
                LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
                wrapper.orderByAsc(Menu::getOrderNum);
                wrapper.orderByAsc(Menu::getParentId);
                List<Menu> list = baseMapper.selectList(wrapper);
                List menuList = MenuHelper.buildTree(list);
                redisService.set("menuList",menuList);
                return menuList;
            }
        }
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public boolean delMenu(Integer id) {
        Menu menu = this.getById(id);
        if (menu.getParentId() == 0){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Menu::getParentId,id);
            long count = this.count(wrapper);
            if (count > 0){
                throw new BizException("该菜单下有子菜单");
            }else {
                this.removeById(id);
                redisService.set("menuList","");
                return true;
            }

        }else {
            this.removeById(menu);
            redisService.set("menuList","");
            return true;
        }
    }

    /**
     * 更新或新增菜单
     * @param menu
     * @return
     */
    @Override
    public boolean updateMenu(Menu menu) {
        this.saveOrUpdate(menu);
        redisService.set("menuList","");
        return true;
    }

}




