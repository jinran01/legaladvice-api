package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fiee.legaladvice.dto.UserDetailDTO;
import com.fiee.legaladvice.entity.UserAuth;
import com.fiee.legaladvice.entity.UserInfo;
import com.fiee.legaladvice.events.UserDetailsUpdatedEvent;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.service.RoleService;
import com.fiee.legaladvice.service.UserAuthService;
import com.fiee.legaladvice.service.UserInfoService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.fiee.legaladvice.utils.IpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.fiee.legaladvice.constant.RedisPrefixConst.*;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.service.impl
 * @Date: 2024/3/10
 * @Version: v1.0.0
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private RoleService roleService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisService redisService;

//    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BizException("用户名不能为空！");
        }
        // 查询账号是否存在
//        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
//                .eq(UserAuth::getUsername, username));
        UserAuth userAuth = userAuthService.getOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, username));
        if (Objects.isNull(userAuth)) {
            throw new BizException("用户名不存在!");
        }

//        applicationEventPublisher.publishEvent(new UserDetailsUpdatedEvent(this, convertUserDetail(userAuth, request)));

        // 封装登录信息
        return convertUserDetail(userAuth, request);
    }

    /**
     * 封装用户登录信息
     *
     * @param user    用户账号
     * @param request 请求
     * @return 用户登录信息
     */
    public UserDetailDTO convertUserDetail(UserAuth user, HttpServletRequest request) {
        // 查询账号信息
        UserInfo userInfo = userInfoService.getById(user.getUserInfoId());
        // 查询账号角色
        List<String> roleList = roleService.listRolesByUserAuthId(user.getId());
        // 查询账号点赞信息
        Set<Object> articleLikeSet = redisService.sMembers(ARTICLE_USER_LIKE + userInfo.getId());
        Set<Object> commentLikeSet = redisService.sMembers(COMMENT_USER_LIKE + userInfo.getId());
        Set<Object> lawyerLikeSet = redisService.sMembers(LAWYER_USER_LIKE + userInfo.getId());
//        Set<Object> talkLikeSet = redisService.sMembers(TALK_USER_LIKE + userInfo.getId());
        // 获取设备信息
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        UserAgent userAgent = IpUtils.getUserAgent(request);
        // 封装权限集合
        return UserDetailDTO.builder()
                .id(user.getId())
                .loginType(user.getLoginType())
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(userInfo.getEmail())
                .phone(userInfo.getPhone())
                .sex(userInfo.getSex())
                .roleList(roleList)
                .name(userInfo.getName())
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .webSite(userInfo.getWebSite())
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .lawyerLikeSet(lawyerLikeSet)
//                .talkLikeSet(null)
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .isDisable(userInfo.getIsDisable())
                .browser(userAgent.getBrowser().getName())
                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")))
                .build();
    }

}