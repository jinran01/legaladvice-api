package com.fiee.legaladvice.handle;

import com.alibaba.fastjson.JSON;
import com.fiee.legaladvice.utils.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fiee.legaladvice.constant.CommonConst.APPLICATION_JSON;


/**
 * @Author: Fiee
 * @ClassName: logoutSuccessHandler
 * @Date: 2023/3/15
 * @Version: v1.0.0
 **/
@Component
public class logoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.ok("成功退出")));
    }
}
