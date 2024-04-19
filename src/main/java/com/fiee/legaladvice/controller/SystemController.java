package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.annotation.AccessLimit;
import com.fiee.legaladvice.properties.AliProperties;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.service.impl.SystemServiceImpl;
import com.fiee.legaladvice.utils.MakeCodeUtils;
import com.fiee.legaladvice.utils.OssUploadUtils;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.fiee.legaladvice.constant.RedisPrefixConst.USER_CODE_KEY;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/3/24
 * @Version: v1.0.0
 **/


@Api(tags = "系统管理")
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private OssUploadUtils ossUploadUtils;
    @Autowired
    private AliProperties aliProperties;

    @ApiOperation("获取OSSPolicy")
    @GetMapping("/oss/policy")
    public Result getPolicy(@RequestParam("path") String path) throws UnsupportedEncodingException {
        return Result.ok(ossUploadUtils.getOssPolicy(
                aliProperties.getEndpoint(),
                aliProperties.getAccessKeyId(),
                aliProperties.getAccessKeySecret(),
                path));
    }
    @AccessLimit(count = 5)
    @ApiOperation("获取邮箱验证码")
    @GetMapping("/email/code")
    public Result getEmailCode(String username) {
        new SystemServiceImpl().sendCode(username);
        return Result.ok();
    }

    @ApiOperation("修改用户邮箱")
    @PostMapping("/user/email/change")
    public Result changeUserEmail(@RequestBody Map<String,String> map) {
        new SystemServiceImpl().changeEmail(map);
        return Result.ok();
    }

    @ApiOperation("注册用户")
    @PostMapping("/user/register")
    public Result userRegister(@RequestBody Map<String,String> map) {
        new SystemServiceImpl().registerUser(map);
        return Result.ok("初始密码为:123456");
    }

    @ApiOperation("忘记密码")
    @PostMapping("/user/forget/pass")
    public Result userForget(@RequestBody Map<String,String> map) {
        new SystemServiceImpl().forgetPass(map);
        return Result.ok();
    }

}
