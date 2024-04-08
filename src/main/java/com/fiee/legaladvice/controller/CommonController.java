package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.service.BlogInfoService;
import com.fiee.legaladvice.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fiee
 * @ClassName: CommonController
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/


@Api(tags = "公共资源管理")
@RestController
public class CommonController {

    @Autowired
    private BlogInfoService blogInfoService;


    /**
     * 上传访客信息
     *
     * @return {@link Result}
     */
    @ApiOperation("访客信息")
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.ok();
    }
}
