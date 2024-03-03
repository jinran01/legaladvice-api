package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.RoleMenu;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_role_menu】的数据库操作Service
* @createDate 2024-03-02 20:03:36
*/
public interface RoleMenuService extends IService<RoleMenu> {
    List getRoleMenus(Integer id);

}
