package com.fiee.legaladvice.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.utils
 * @Date: 2024/3/24
 * @Version: v1.0.0
 **/

@Component
public class OssUploadUtils {

    public Map getOssPolicy(String endpoint,String accessKeyId,String accessKeySecret,String path) throws UnsupportedEncodingException {

        OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);
        String host = "https://" + "legaladvice" + "." + endpoint;
        Map<String, String> respMap = new LinkedHashMap<>();
        long expireTime = 60;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);

        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE,0,1048576000);
        policyConds.addConditionItem(MatchMode.StartWith,PolicyConditions.COND_KEY,path+'/');
        String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);
        respMap.put("accessKeyId",accessKeyId);
        respMap.put("policy",encodedPolicy);
        respMap.put("signature",postSignature);
        respMap.put("dir",path+'/');
        respMap.put("host",host);
        respMap.put("expire",String.valueOf(expireEndTime /1000));
        ossClient.shutdown();
        return respMap;
    }

}
