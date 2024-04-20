package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.annotation.AccessLimit;
import com.fiee.legaladvice.entity.UserInfo;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.service.RedisService;
import com.fiee.legaladvice.service.UserInfoService;
import com.fiee.legaladvice.mapper.UserInfoMapper;
import com.fiee.legaladvice.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

import static com.fiee.legaladvice.constant.RedisPrefixConst.USER_CODE_KEY;

/**
* @author Fiee
* @description 针对表【tb_user_info】的数据库操作Service实现
* @createDate 2024-03-02 19:39:16
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{
    @Autowired
    private RedisService redisService;

    @Override
    public void saveUserPhone(Map<String, String> map) {
        UserInfo userInfo = this.getById(UserUtils.getLoginUser().getUserInfoId());
        String phone = map.get("phone");
        if (Objects.isNull(userInfo.getPhone())){
            if (!map.get("code").equals(redisService.get(USER_CODE_KEY+phone))){
                throw new BizException("验证码错误！");
            }
        }else {
            if (!map.get("code").equals(redisService.get(USER_CODE_KEY+userInfo.getPhone()))){
                throw new BizException("验证码错误！");
            }
        }
        userInfo.setPhone(phone);
        this.saveOrUpdate(userInfo);
    }
}




