package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.UserInfo;
import com.fiee.legaladvice.service.UserInfoService;
import com.fiee.legaladvice.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Fiee
* @description 针对表【tb_user_info】的数据库操作Service实现
* @createDate 2024-03-02 19:39:16
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




