package com.fiee.legaladvice.controller;

import com.fiee.legaladvice.properties.AliProperties;
import com.fiee.legaladvice.utils.OssUploadUtils;
import com.fiee.legaladvice.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.controller
 * @Date: 2024/3/24
 * @Version: v1.0.0
 **/


@Api(tags = "系统管理")
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private OssUploadUtils ossUploadUtils;
    @Autowired
    private AliProperties aliProperties;

    @ApiOperation("获取OSSPolicy")
    @GetMapping("/oss/policy")
    public Result getPolicy(@RequestParam("path") String path) throws UnsupportedEncodingException {
        System.out.println(path);
        return Result.ok(ossUploadUtils.getOssPolicy(
                aliProperties.getEndpoint(),
                aliProperties.getAccessKeyId(),
                aliProperties.getAccessKeySecret(),
                path));
    }

}
