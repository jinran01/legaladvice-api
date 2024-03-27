package com.fiee.legaladvice.handle;

import com.alibaba.fastjson.JSON;
import com.fiee.legaladvice.utils.Result;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.security.web.session.InvalidSessionStrategy;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fiee.legaladvice.constant.CommonConst.APPLICATION_JSON;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.handle
 * @Date: 2024/3/16
 * @Version: v1.0.0
 **/
public class MyInvalSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.setContentType(APPLICATION_JSON);
        throw new ServletException("Session Invalid");
//        response.getWriter().write(JSON.toJSONString(Result.fail("Session Invalid")));
    }
}
