package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.entity.LawyerAuth;
import com.fiee.legaladvice.service.LawyerAuthService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ConditionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Fiee
 * @ClassName: LawyerAuthController
 * @Date: 2024/4/23
 * @Version: v1.0.0
 **/

@Api(tags = "律师认证管理")
@RestController
public class LawyerAuthController {
    @Autowired
    private LawyerAuthService lawyerAuthService;

    @ApiOperation(value = "后端获取认证列表")
    @GetMapping("/admin/lawyer")
    public Result getLawyerList(ConditionVO vo){
        return Result.ok(lawyerAuthService.getLawyerList(vo));
    }

    @ApiOperation(value = "后端修改认证状态")
    @PostMapping("/admin/lawyer/status")
    public Result changeAuthStatus(@RequestBody LawyerAuth lawyerAuth){
        lawyerAuthService.updateLawyerAuth(lawyerAuth);
        return Result.ok("更改状态成功");
    }
}
