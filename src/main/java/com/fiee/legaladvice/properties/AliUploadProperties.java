package com.fiee.legaladvice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.properties
 * @Date: 2024/3/24
 * @Version: v1.0.0
 **/

@Data
@Configuration
@Component
@ConfigurationProperties("upload.oss")
public class AliUploadProperties {
    //url
    private String url;
    //区域
    private String endpoint;
    //访问id
    private String accessKeyId;
    //访问秘钥
    private String accessKeySecret;
    //桶名称
    private String bucketName;
}
