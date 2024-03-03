package com.fiee.legaladvice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.entity.UserRole;
import com.fiee.legaladvice.service.UserRoleService;
import com.fiee.legaladvice.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Fiee
* @description 针对表【tb_user_role】的数据库操作Service实现
* @createDate 2024-03-02 19:40:57
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

}




