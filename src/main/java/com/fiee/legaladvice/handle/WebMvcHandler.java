package com.fiee.legaladvice.handle;

import com.alibaba.fastjson.JSON;

import com.fiee.legaladvice.annotation.AccessLimit;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.utils.IpUtils;
import com.fiee.legaladvice.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static com.fiee.legaladvice.constant.CommonConst.APPLICATION_JSON;


/**
 * @Author: Fiee
 * @ClassName: WebMvcHandler
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Slf4j
public class WebMvcHandler implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit limit = hm.getMethodAnnotation(AccessLimit.class);
            if (limit != null){
                Long seconds = Long.valueOf(limit.seconds());
                int maxCount = limit.count();
                // 关于key的生成规则可以自己定义 本项目需求是对每个方法都加上限流功能，如果你只是针对ip地址限流，那么key只需要只用ip就好
                String key = IpUtils.getIpAddress(request) + "-" + hm.getMethod().getName();
                //从redis中获取用户请求次数
                try{
                    Long count = redisService.incrExpire(key, seconds);
                    if (count > maxCount){
                        render(response, Result.fail(limit.massage()));
                        log.warn(key + "请求次数超过每" + seconds + "秒" + maxCount + "次");
                        return false;
                    }
                    return true;
                }catch (RedisConnectionFailureException e){
                    log.warn("redis错误: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
    private void render(HttpServletResponse response, Result<?> result) throws Exception {
        response.setContentType(APPLICATION_JSON);
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(result);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
