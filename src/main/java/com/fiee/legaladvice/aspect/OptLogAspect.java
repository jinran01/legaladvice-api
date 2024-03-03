package com.fiee.legaladvice.aspect;

import com.alibaba.fastjson.JSON;



import com.fiee.legaladvice.annotation.OptLog;
import com.fiee.legaladvice.entity.OperationLog;
import com.fiee.legaladvice.service.OperationLogService;
import com.fiee.legaladvice.utils.IpUtils;
import com.fiee.legaladvice.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author: Fiee
 * @ClassName: OptLogAspect 操作日志切面处理
 * @Date: 2024/3/1
 * @Version: v1.0.0
 **/

@Aspect
@Component
public class OptLogAspect {
    @Autowired
    private OperationLogService operationLogService;

    @Pointcut("@annotation(com.fiee.legaladvice.annotation.OptLog)")
    public void optLogPointcut(){}

    @AfterReturning(value = "optLogPointcut()",returning = "keys")
    @SuppressWarnings("unchecked")
    public void savaOptLog(JoinPoint joinPoint, Object keys){
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes)
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        OperationLog operationLog = new OperationLog();
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 获取操作
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        OptLog optLog = method.getAnnotation(OptLog.class);
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法
        String methodName = method.getName();
        methodName = className + "." + methodName;
        //操作模块
        operationLog.setOptModule(api.tags()[0]);
        //操作类型
        operationLog.setOptType(optLog.optType());
        //操作描述
        operationLog.setOptDesc(apiOperation.value());
        //请求方法
        operationLog.setOptMethod(methodName);
        //请求方式
        operationLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        //请求参数
        operationLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
        //返回结果
        operationLog.setResponseData(JSON.toJSONString(keys));
        //请求用户id
        operationLog.setUserId(UserUtils.getLoginUser().getId());
        //请求用户昵称
        operationLog.setNickname(UserUtils.getLoginUser().getNickname());
        //请求IP
        String ipAddress = IpUtils.getIpAddress(request);
        operationLog.setIpAddress(ipAddress);
        //请求IP地址
        operationLog.setIpSource(IpUtils.getIpSource(ipAddress));
        operationLog.setOptUrl(request.getRequestURI());
        operationLogService.save(operationLog);
    }
}
