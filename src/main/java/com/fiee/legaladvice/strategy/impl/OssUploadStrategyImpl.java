package com.fiee.legaladvice.strategy.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.fiee.legaladvice.properties.AliUploadProperties;
import com.fiee.legaladvice.utils.OssUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author: Fiee
 * @ClassName: OssUploadStrategyImpl
 * @Date: 2024/4/29
 * @Version: v1.0.0
 **/
@Service("ossUploadStrategyImpl")
public class OssUploadStrategyImpl extends AbstractUploadStrategyImpl{
    @Autowired
    private AliUploadProperties aliUploadProperties;



    @Override
    public Boolean exists(String filePath) {
        return getOssClient().doesObjectExist(aliUploadProperties.getBucketName(),filePath);
    }


    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
        getOssClient().putObject(aliUploadProperties.getBucketName(),
                path + fileName,
                inputStream,
                objectMetadata
        );
    }

    @Override
    public Map getOssPolicy(String path) throws UnsupportedEncodingException {
        return new OssUploadUtils().getOssPolicy(
                aliUploadProperties.getEndpoint(),
                aliUploadProperties.getAccessKeyId(),
                aliUploadProperties.getAccessKeySecret(),
                path
        );
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return aliUploadProperties.getUrl() + filePath;
    }
    /**
     * 获取ossClient
     *
     * @return {@link OSS} ossClient
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(
                aliUploadProperties.getEndpoint(),
                aliUploadProperties.getAccessKeyId(),
                aliUploadProperties.getAccessKeySecret()
        );
    }
}
