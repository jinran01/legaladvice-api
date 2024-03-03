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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
//    @Value("${upload.oss.endpoint}")
//    public String endpoint;
//    @Value("${upload.oss.accessKeySecret}")
//    public String accessKeySecret;
//    @Value("${upload.oss.accessKeyId}")
//    public String accessKeyId;
//    @Value("${upload.oss.bucketName}")
//    public String bucketName;

    @ApiOperation("用户列表")
    @GetMapping("/users")
    public Result<PageResult<UserBackDTO>> getUserList(ConditionVO condition) {
        PageResult<UserBackDTO> pageResult = userAuthService.getUserList(condition);
        System.out.println(pageResult);
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
        userAuthService.removeUser(userInfoId);
        return Result.fail("操作成功！");
    }

//    @ApiOperation("获取AvatarOssToken")
//    @GetMapping("/getAvatarOssToken")
//    public Result getOssToken() throws UnsupportedEncodingException {
//        OssUtils ossUtils = new OssUtils();
//        Map ossToken = ossUtils.getOssToken(endpoint,accessKeyId, accessKeySecret,bucketName,AVATAR.getPath());
//        return Result.ok(ossToken);
//    }
    @OptLog(optType = UPDATE)
    @ApiOperation("修改用户")
    @PostMapping("/users/avatar")
    public Result updateUserAvatar(@RequestBody UserInfo userInfo){
        //TODO
        //未实现修改头像功能
        return Result.ok();
//        return Result.ok(userInfoService.updateUserAvatar(userInfo));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("修改用户信息")
    @PostMapping("/users/info")
    public Result updateUserInfo(@RequestBody Map map){
        boolean flag = userAuthService.updateUserInfo(map);
        if (flag){
            return Result.ok(userAuthService.updateUserInfo(map));
        }else {
            return Result.fail("旧密码不正确");
        }
    }
    @AccessLimit(count = 3)
    @PostMapping("/users/send")
    public Result sendMailCode(@RequestBody String username){
        userAuthService.sendCode(username);
        return Result.ok("发送成功");
    }

}