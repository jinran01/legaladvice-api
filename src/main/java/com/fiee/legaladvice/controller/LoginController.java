package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.utils.MakeCodeUtils;
import com.fiee.legaladvice.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/3/10
 * @Version: v1.0.0
 **/

@Api(tags = "登录验证管理")
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private RedisService redisService;

    @ApiOperation("获取登录验证码")
    @GetMapping("/getLoginCode/{uuid}")
    public void getLoginCode(HttpServletResponse response,@PathVariable String uuid) throws IOException {
        String code = MakeCodeUtils.makeCode(response);
        redisService.set(uuid,code,30);
    }

    @ApiOperation("检验登录验证码")
    @PostMapping("/checkLoginCode/")
    public Result checkLoginCode(@RequestBody Map<String,String> codeInfo){
        String uuid = codeInfo.get("uuid");
        String userCode = codeInfo.get("code");
        String code = redisService.get(uuid).toString();
        if (MakeCodeUtils.checkCode(code,userCode)){
            return Result.ok();
        }else {
            return Result.fail("验证码错误或者过期！");
        }
    }
//    @GetMapping("/session/invalid")
//    public Result sessionInvalid(){
//        return Result.fail("session invalid");
//    }
}
