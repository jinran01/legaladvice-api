package com.fiee.legaladvice.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

/**
 * @Author: Fiee
 * @ClassName: AliyunUtils
 * @Date: 2024/4/20
 * @Version: v1.0.0
 **/
@Component
public class AliyunUtils {
    public String getPhoneCode(String accessKeyId,String accessKeySecret,
                               String templateCode,String signName,String phone){
        //获取6位验证码
        String code = CommonUtils.getRandomCode(6);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setTemplateCode(templateCode);
        request.setSignName(signName);
        request.setTemplateParam("{" + "code" + ":" + "'" + code +"'" +"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return code;
    }
}
