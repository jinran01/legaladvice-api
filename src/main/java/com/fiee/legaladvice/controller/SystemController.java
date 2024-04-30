package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.annotation.AccessLimit;
import com.fiee.legaladvice.properties.AliPhoneProperties;
import com.fiee.legaladvice.properties.AliUploadProperties;
import com.fiee.legaladvice.service.BlogInfoService;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.service.UserInfoService;
import com.fiee.legaladvice.service.impl.SystemServiceImpl;
import com.fiee.legaladvice.strategy.UploadStrategy;
import com.fiee.legaladvice.strategy.context.UploadStrategyContext;
import com.fiee.legaladvice.utils.AliyunUtils;
import com.fiee.legaladvice.utils.OssUploadUtils;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

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
    private AliyunUtils aliyunUtils;
    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private AliPhoneProperties aliPhoneProperties;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;
    @AccessLimit(count = 5)
    @ApiOperation("获取OSSPolicy")
    @GetMapping("/oss/policy")
    public Result getPolicy(@RequestParam("path") String path) throws UnsupportedEncodingException {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(path));
    }
    @AccessLimit(count = 5)
    @ApiOperation("获取短信验证码")
    @GetMapping("/phone/code")
    public Result getPhoneCode(@RequestParam("phone") String phone){
        String getPhone = userInfoService.getById(UserUtils.getLoginUser().getUserInfoId()).getPhone();
        if (!Objects.isNull(getPhone)){
            phone = getPhone;
        }
        String code = aliyunUtils.getPhoneCode(
                aliPhoneProperties.getAccessKeyId(),
                aliPhoneProperties.getAccessKeySecret(),
                aliPhoneProperties.getTemplateCode(),
                aliPhoneProperties.getSignName(),
                phone);
        redisService.set(USER_CODE_KEY+phone,code,5*60);
        return Result.ok();
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
    /**
     * 上传访客信息
     *
     * @return {@link Result}
     */
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.ok();
    }
}
