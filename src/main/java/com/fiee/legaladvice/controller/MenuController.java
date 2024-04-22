package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.entity.Menu;
import com.fiee.legaladvice.service.MenuService;
import com.fiee.legaladvice.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.fiee.legaladvice.constant.OptTypeConst.*;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/3/3
 * @Version: v1.0.0
 **/
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation("菜单列表")
    @GetMapping("/menus")
    public Result getUserList(String menuName){
        return Result.ok(menuService.getMenuList(menuName));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation("更新或新增菜单")
    @PostMapping("/menus")
    public Result getUserList(@RequestBody Menu menu){
        return Result.ok(menuService.updateMenu(menu));
    }

    @OptLog(optType = REMOVE)
    @ApiOperation("删除菜单")
    @DeleteMapping("/menus/{id}")
    public Result getUserList(@PathVariable Integer id){
        return Result.ok(menuService.delMenu(id));
    }

}
