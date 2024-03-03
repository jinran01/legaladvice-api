package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.entity.Role;
import com.fiee.legaladvice.entity.RoleResource;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_role_resource】的数据库操作Service
* @createDate 2024-03-02 20:03:39
*/
public interface RoleResourceService extends IService<RoleResource> {
    List getRoleResource(Role role);
}
