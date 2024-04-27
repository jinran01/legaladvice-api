package com.fiee.legaladvice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiee.legaladvice.entity.LawyerAuth;
import com.fiee.legaladvice.service.LawyerAuthService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.utils.UserUtils;
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

    @ApiOperation(value = "后台获取认证列表")
    @GetMapping("/admin/lawyer")
    public Result getLawyerList(ConditionVO vo){
        return Result.ok(lawyerAuthService.getLawyerList(vo));
    }

    @ApiOperation(value = "后台修改认证状态")
    @PostMapping("/admin/lawyer/status")
    public Result changeAuthStatus(@RequestBody LawyerAuth lawyerAuth){
        lawyerAuthService.updateLawyerAuth(lawyerAuth);
        return Result.ok("更改状态成功");
    }

    @ApiOperation(value = "律师申请认证")
    @PostMapping("/lawyer/auth")
    public Result lawyerAuth(@RequestBody LawyerAuth lawyerAuth){
        lawyerAuthService.savaOrUpdateAuth(lawyerAuth);
        return Result.ok("提交成功,待审核中...");
    }

    @ApiOperation(value = "律师申请认证信息")
    @GetMapping("/lawyer/info")
    public Result lawyerInfo(){
        LambdaQueryWrapper<LawyerAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LawyerAuth::getUserAuthId,UserUtils.getLoginUser().getId());
        LawyerAuth lawyerAuth = lawyerAuthService.getOne(wrapper);
        return Result.ok(lawyerAuth);
    }

    @ApiOperation(value = "前台获取律师列表")
    @GetMapping("/lawyer/list")
    public Result getHomeLawyerList(ConditionVO vo){
        return Result.ok(lawyerAuthService.getHomeLawyerList(vo));
    }

    @ApiOperation(value = "获取聊天对象列表")
    @GetMapping("/chat/user/list")
    public Result getChatUserList(){
        return Result.ok(lawyerAuthService.getChatUserList());
    }
}
