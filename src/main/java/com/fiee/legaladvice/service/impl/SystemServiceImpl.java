package com.fiee.legaladvice.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiee.legaladvice.dto.EmailDTO;
import com.fiee.legaladvice.entity.IncreateData;
import com.fiee.legaladvice.entity.UserAuth;
import com.fiee.legaladvice.entity.UserInfo;
import com.fiee.legaladvice.entity.UserRole;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.service.*;
import com.fiee.legaladvice.utils.UserUtils;
import javafx.scene.input.DataFormat;
import org.apache.catalina.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fiee.legaladvice.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.fiee.legaladvice.constant.RedisPrefixConst.*;
import static com.fiee.legaladvice.enums.EmailEnum.COMMON_CODE;
import static com.fiee.legaladvice.utils.CommonUtils.checkEmail;
import static com.fiee.legaladvice.utils.CommonUtils.getRandomCode;

/**
 * @Author: Fiee
 * @ClassName: SystemServiceImpl
 * @Date: 2024/4/17
 * @Version: v1.0.0
 **/
@Service
@Component
public class SystemServiceImpl {


    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) {
        SystemServiceImpl.userAuthService = userAuthService;
    }
    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        SystemServiceImpl.userInfoService = userInfoService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        SystemServiceImpl.userRoleService = userRoleService;
    }

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate){
        SystemServiceImpl.rabbitTemplate = rabbitTemplate;
    }
    @Autowired
    private void setRedisService(RedisService redisService){
        SystemServiceImpl.redisService = redisService;
    }
    private static RabbitTemplate rabbitTemplate;
    private static RedisService redisService;
    private static UserAuthService userAuthService;
    private static UserInfoService userInfoService;
    private static UserRoleService userRoleService;


    /**
     * 发送邮箱验证码
     * @param username
     */
    public void sendCode(String username){
        if (!checkEmail(username)) {
            throw new BizException("请输入正确邮箱");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode(6);

        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("验证码")
                .template(COMMON_CODE.getTemplate())
                .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
    }

    @Transactional
    public void registerUser(Map<String,String> map){
        if (!map.get("code").equals(redisService.get(USER_CODE_KEY+map.get("username")))){
            throw new BizException("验证码错误！");
        }
        LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAuth::getUsername,map.get("username"));
        if (Objects.isNull(userAuthService.getOne(wrapper))){
            //创建userInfo
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(map.get("username"));
            userInfo.setAvatar(map.get("avatar"));
            userInfo.setNickname(map.get("username"));
            userInfoService.save(userInfo);
            //创建userAuth
            UserAuth userAuth = new UserAuth();
            userAuth.setUsername(map.get("username"));
            userAuth.setPassword(new BCryptPasswordEncoder().encode(map.get("password")));
            userAuth.setUserInfoId(userInfo.getId());
            userAuthService.save(userAuth);
            //添加角色
            UserRole userRole = new UserRole();
            userRole.setUserId(userAuth.getId());
            userRole.setRoleId(3);
            userRoleService.save(userRole);
            redisService.incr(INCREATE_USER,1L);
        }else {
            throw new BizException("该邮箱已被注册");
        }
    }
    @Transactional
    public void forgetPass(Map<String, String> map) {
        LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAuth::getUsername,map.get("username"));
        UserAuth userAuth = userAuthService.getOne(wrapper);
        if (Objects.isNull(userAuth)){
            throw new BizException("该用户不存在！");
        }else {
            userAuth.setPassword(new BCryptPasswordEncoder().encode(map.get("password")));
            userAuthService.saveOrUpdate(userAuth);
        }
    }

    /**
     * 用户修改邮箱
     * @param map
     */
    @Transactional
    public void changeEmail(Map<String,String> map) {
        LambdaQueryWrapper<UserAuth> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAuth::getUsername,map.get("username"));
        if (Objects.isNull(userAuthService.getOne(wrapper))){
            UserAuth userAuth = userAuthService.getById(UserUtils.getLoginUser().getId());
            if (!map.get("code").equals(redisService.get(USER_CODE_KEY+userAuth.getUsername()))){
                throw new BizException("验证码错误！");
            }
            UserInfo userInfo = userInfoService.getById(UserUtils.getLoginUser().getUserInfoId());
            userAuth.setUsername(map.get("username"));
            userInfo.setEmail(map.get("username"));
            userAuthService.saveOrUpdate(userAuth);
            userInfoService.saveOrUpdate(userInfo);
        }else {
            throw new BizException("该邮箱已被注册");
        }
    }

}
