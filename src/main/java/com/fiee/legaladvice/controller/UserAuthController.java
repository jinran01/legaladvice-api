package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.annotation.AccessLimit;
import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.dto.UserBackDTO;
import com.fiee.legaladvice.entity.UserAuth;
import com.fiee.legaladvice.entity.UserInfo;
import com.fiee.legaladvice.service.UserAuthService;
import com.fiee.legaladvice.service.UserInfoService;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.fiee.legaladvice.constant.OptTypeConst.*;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/3/3
 * @Version: v1.0.0
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin")
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("用户列表")
    @GetMapping("/users")
    public Result<PageResult<UserBackDTO>> getUserList(ConditionVO condition) {
        PageResult<UserBackDTO> pageResult = userAuthService.getUserList(condition);
        return Result.ok(pageResult);
    }
    @OptLog(optType = UPDATE)
    @ApiOperation("修改用户角色")
    @PutMapping("/user/role")
    public Result updateUserRole(@RequestBody Map<String,Object> map) {
        return Result.ok(userAuthService.updateUserRole(map));
    }
    @OptLog(optType = UPDATE)
    @ApiOperation("用户禁用状态修改")
    @PutMapping("/users/disable")
    public Result updateState(@RequestBody UserAuth userAuth) {
        System.out.println(userAuth);
        if (userAuthService.updateUserState(userAuth)) {
            return Result.ok(null, "修改成功！");
        } else {
            return Result.fail("修改失败！");
        }
    }

    @ApiOperation("在线用户")
    @GetMapping("/users/online")
    public Result getOnlineUsers(ConditionVO conditionVO) {
        return Result.ok(userAuthService.getOnlineUsers(conditionVO));
    }
    @OptLog(optType = REMOVE)
    @ApiOperation("下线用户")
    @DeleteMapping("/users/{userInfoId}/online")
    public Result removeUser(@PathVariable Integer userInfoId) {
        String str = userAuthService.removeUser(userInfoId);
        return Result.ok(str);
    }
    @OptLog(optType = UPDATE)
    @ApiOperation("更新后台用户头像")
    @PostMapping("/users/avatar")
    public Result updateUserAvatar(@RequestBody UserInfo userInfo){
        return Result.ok(userInfoService.updateById(userInfo));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("修改用户密码")
    @PostMapping("/users/pass")
    public Result updateUserPass(@RequestBody Map map){
        boolean flag = userAuthService.updateUserPass(map);
        if (flag){
            return Result.ok(userAuthService.updateUserPass(map));
        }else {
            return Result.fail("旧密码不正确");
        }
    }
    @OptLog(optType = UPDATE)
    @ApiOperation("更新后台用户信息")
    @PostMapping("/users/info")
    public Result updateUserInfo(@RequestBody UserInfo userInfo){
        return Result.ok(userAuthService.updateUserInfo(userInfo));
    }

    /**
     * 获取当前用户信息
     * @param authentication
     * @return
     */
    @ApiOperation("获取当前用户信息")
    @GetMapping("/users/info")
    public Result getUserInfo(Authentication authentication){
        return Result.ok(authentication.getPrincipal());
    }

}