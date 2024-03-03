package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Fiee
* @description 针对表【tb_user_role】的数据库操作Mapper
* @createDate 2024-03-02 19:40:57
* @Entity com.fiee.legaladvice.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




