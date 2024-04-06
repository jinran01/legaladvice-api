package com.fiee.legaladvice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiee.legaladvice.entity.Category;
import com.fiee.legaladvice.entity.Tag;
import com.fiee.legaladvice.service.CategoryService;
import com.fiee.legaladvice.service.TagService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @Author: Fiee
 * @ClassName: TagController
 * @Date: 2023/5/14
 * @Version: v1.0.0
 **/
@Api(tags = "标签管理")
@RestController
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation("搜索文章标签")
    @GetMapping("/tags/search")
    public Result searchCategory(ConditionVO vo){
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(vo.getKeywords() != null,Tag::getTagName,vo.getKeywords());
        return Result.ok(tagService.list(wrapper));
    }

    @ApiOperation("查看后台标签列表")
    @GetMapping("/tags")
    public Result categoryList(ConditionVO vo){
        return Result.ok(tagService.getTagList(vo));
    }

    @ApiOperation("添加或修改标签")
    @PostMapping("/tags")
    public Result saveOrUpdateCategory(@RequestBody Tag tag){
        return Result.ok(tagService.saveOrUpdate(tag));
    }

    @ApiOperation("删除标签")
    @DeleteMapping("/tags")
    public Result searchCategory(@RequestBody Long[] tags){
        return Result.ok(tagService.removeBatchByIds(Arrays.asList(tags)));
    }
}
