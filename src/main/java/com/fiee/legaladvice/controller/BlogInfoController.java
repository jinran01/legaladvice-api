package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.dto.BlogBackInfoDTO;
import com.fiee.legaladvice.service.BlogInfoService;
import com.fiee.legaladvice.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Fiee
 * @ClassName: blogInfoController
 * @Date: 2024/4/8
 * @Version: v1.0.0
 **/

@Api(tags = "博客信息模块")
@RestController
@RequestMapping("/admin")
public class BlogInfoController {
    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 查看后台信息
     *
     * @return {@link Result<BlogBackInfoDTO>} 后台信息
     */
    @ApiOperation(value = "查看后台信息")
    @GetMapping("/")
    public Result<BlogBackInfoDTO> getBlogBackInfo() {
        return Result.ok(blogInfoService.getBlogBackInfo());
    }
}
