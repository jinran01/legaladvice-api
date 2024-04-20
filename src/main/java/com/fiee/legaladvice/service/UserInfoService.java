package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.UserInfo;

import java.util.Map;

/**
* @author Fiee
* @description 针对表【tb_user_info】的数据库操作Service
* @createDate 2024-03-02 19:39:16
*/
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 修改或绑定手机号
     * @param map
     */
    void saveUserPhone(Map<String, String> map);
}
