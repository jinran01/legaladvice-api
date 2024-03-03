package com.fiee.legaladvice.handle;


import com.alibaba.fastjson.JSON;

import com.fiee.legaladvice.dto.UserInfoDTO;
import com.fiee.legaladvice.entity.UserAuth;
import com.fiee.legaladvice.mapper.UserAuthMapper;
import com.fiee.legaladvice.utils.BeanCopyUtils;
import com.fiee.legaladvice.utils.JwtUtil;
import com.fiee.legaladvice.utils.Result;
import com.fiee.legaladvice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.fiee.legaladvice.constant.CommonConst.APPLICATION_JSON;


/**
 * @Author: Fiee
 * @ClassName: AuthenticationSuccessHandlerImpl
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        // 返回登录信息
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getLoginUser(), UserInfoDTO.class);
//        UserDetailDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getLoginUser(), UserDetailDTO.class);
        httpServletResponse.setContentType(APPLICATION_JSON);
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo",userLoginDTO);
        String token = JwtUtil.createJWT(UserUtils.getLoginUser().getId().toString());
        map.put("token",token);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.ok(map)));
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }

    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtils.getLoginUser().getId())
                .ipAddress(UserUtils.getLoginUser().getIpAddress())
                .ipSource(UserUtils.getLoginUser().getIpSource())
                .updateTime(UserUtils.getLoginUser().getLastLoginTime())
                .lastLoginTime(UserUtils.getLoginUser().getLastLoginTime())
                .build();
        userAuthMapper.updateById(userAuth);
    }

}
