package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.dto.UserAreaDTO;
import com.fiee.legaladvice.service.BlogInfoService;
import com.fiee.legaladvice.service.UserAuthService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    private UserAuthService userAuthService;

    /**
     * 获取用户区域分布
     * @return {@link Result<UserAreaDTO>} 用户区域分布
     */
    @ApiOperation(value = "获取用户区域分布")
    @GetMapping("/admin/users/area")
    public Result<List<UserAreaDTO>> listUserAreas() {
        return Result.ok(userAuthService.listUserAreas());
    }
}
