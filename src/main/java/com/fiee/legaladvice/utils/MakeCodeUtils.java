package com.fiee.legaladvice.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.utils
 * @Date: 2024/3/10
 * @Version: v1.0.0
 **/
@Component
public  class MakeCodeUtils {
//    @Autowired
//    private RedisService redisService;
    public static String makeCode(HttpServletResponse response,String uuid) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(132, 38, 4, 4);
        String code = captcha.getCode();
//        redisService.set(uuid,code);
        captcha.write(response.getOutputStream());
        return code;
    }

    //检验验证码是否正确
    public static boolean checkCode(String code,String userCode){
        return code.equalsIgnoreCase(userCode);
    }
}
