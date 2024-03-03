package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.entity.Resource;
import com.fiee.legaladvice.service.ResourceService;
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
@Api(tags = "资源管理")
@RestController
@RequestMapping("/admin")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation("资源列表")
    @GetMapping("/resources")
    public Result getResourceList(String resourceName){
        return Result.ok(resourceService.getResourceList(resourceName));
    }

    //    @ApiOperation("资源查询")
//    @GetMapping("/resource")
//    public Result searchResource(@RequestParam String resourceName){
//        return Result.ok(resourceService.getResource(resourceName));
//    }
    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation("新增或修改资源")
    @PostMapping("/resources")
    public Result updateResource(@RequestBody Resource resource){
        return Result.ok(resourceService.updateResource(resource));
    }
    @OptLog(optType = REMOVE)
    @ApiOperation("删除资源")
    @DeleteMapping ("/resources/{id}")
    public Result updateResource(@PathVariable Integer id){
        return Result.ok(resourceService.delResource(id));
    }
}