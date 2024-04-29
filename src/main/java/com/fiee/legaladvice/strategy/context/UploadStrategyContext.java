package com.fiee.legaladvice.strategy.context;

import com.fiee.legaladvice.strategy.UploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.fiee.legaladvice.enums.UploadModeEnum.getStrategy;

/**
 * @Author: Fiee
 * @ClassName: UploadStrategyContext
 * @Date: 2024/4/29
 * @Version: v1.0.0
 **/
@Service
public class UploadStrategyContext {
    /**
     * 上传模式
     */
    @Value("${upload.mode}")
    private String uploadMode;

    @Autowired
    private Map<String, UploadStrategy> uploadStrategyMap;


    /**
     * 执行上传策略
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(getStrategy(uploadMode)).uploadFile(file, path);
    }


    /**
     * 执行上传策略
     *
     * @param fileName    文件名称
     * @param inputStream 输入流
     * @param path        路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(String fileName, InputStream inputStream, String path) {
        return uploadStrategyMap.get(getStrategy(uploadMode)).uploadFile(fileName, inputStream, path);
    }

    /**
     * 执行上传策略
     */
    public Map executeUploadStrategy(String path) throws UnsupportedEncodingException {
        return uploadStrategyMap.get(getStrategy(uploadMode)).getOssPolicy(path);
    }
}
