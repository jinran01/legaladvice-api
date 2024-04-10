package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.entity.Page;
import com.fiee.legaladvice.service.PageService;
import com.fiee.legaladvice.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/4/11
 * @Version: v1.0.0
 **/

@RestController
@RequestMapping("admin/")
@Api(tags = "页面管理")
public class PageController {
    @Autowired
    private PageService pageService;

    @ApiOperation("获取页面列表")
    @GetMapping("pages")
    public Result getPage(){
        return Result.ok(pageService.list());
    }

    @ApiOperation("更新或者保存页面")
    @PostMapping("pages")
    public Result saveOrUpdatePage(@RequestBody Page page){
        return Result.ok(pageService.saveOrUpdate(page));
    }

    @ApiOperation("删除页面")
    @DeleteMapping("pages/{id}")
    public Result delPage(@PathVariable Integer id){
        return Result.ok(pageService.removeById(id));
    }

}
