package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.Menu;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_menu】的数据库操作Service
* @createDate 2024-03-03 20:28:22
*/
public interface MenuService extends IService<Menu> {

    List<Menu> getMenuList(String menuName);

    boolean delMenu(Integer id);

    boolean updateMenu(Menu menu);
}
