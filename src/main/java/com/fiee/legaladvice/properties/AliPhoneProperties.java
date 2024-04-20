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
@ConfigurationProperties("aliyun.phone")
public class AliPhoneProperties {
    //访问id
    private String accessKeyId;
    //访问秘钥
    private String accessKeySecret;
    //模板code
    private String templateCode;
    //签名
    private String signName;
}
