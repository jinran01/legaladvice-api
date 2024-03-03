package com.fiee.legaladvice.handle;

import com.alibaba.fastjson.JSON;

import com.fiee.legaladvice.utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fiee.legaladvice.constant.CommonConst.APPLICATION_JSON;


/**
 * @Author: Fiee
 * @ClassName: AccessDeniedHandlerImpl
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail("权限不足")));
    }

}
