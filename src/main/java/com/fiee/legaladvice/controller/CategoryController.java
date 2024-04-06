package com.fiee.legaladvice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.service.CategoryService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ArticleVO;
import com.fiee.legaladvice.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @Author: Fiee
 * @ClassName: CategoryController
 * @Date: 2023/5/14
 * @Version: v1.0.0
 **/
@Api(tags = "分类管理")
@RestController
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("搜索文章分类")
    @GetMapping("/categories/search")
    public Result searchCategory(ConditionVO vo){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(vo.getKeywords() != null,Category::getCategoryName,vo.getKeywords());
        return Result.ok(categoryService.list(wrapper));
    }

    @ApiOperation("查看后台分类列表")
    @GetMapping("/categories")
    public Result categoryList(ConditionVO vo){
        return Result.ok(categoryService.getCategoryList(vo));
    }

    @ApiOperation("添加或修改分类")
    @PostMapping("/categories")
    public Result saveOrUpdateCategory(@RequestBody Category category){
        return Result.ok(categoryService.saveOrUpdate(category));
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/categories")
    public Result searchCategory(@RequestBody Long[] categories){
        return Result.ok(categoryService.removeBatchByIds(Arrays.asList(categories)));
    }

}
