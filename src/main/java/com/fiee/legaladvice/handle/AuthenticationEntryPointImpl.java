package com.fiee.legaladvice.handle;

import com.alibaba.fastjson.JSON;

import com.fiee.legaladvice.enums.StatusCodeEnum;
import com.fiee.legaladvice.utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fiee.legaladvice.constant.CommonConst.APPLICATION_JSON;


/**
 * @Author: Fiee
 * @ClassName: AuthenticationEntryPointImpl
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail(StatusCodeEnum.NO_LOGIN)));
    }

}
