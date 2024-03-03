package com.fiee.legaladvice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiee.legaladvice.dto.ResourceRoleDTO;
import com.fiee.legaladvice.dto.RoleDTO;
import com.fiee.legaladvice.entity.Role;
import com.fiee.legaladvice.vo.ConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Fiee
* @description 针对表【tb_role】的数据库操作Mapper
* @createDate 2024-03-02 19:42:01
* @Entity com.fiee.legaladvice.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<String> listRolesByUserInfoId(Integer userInfoId);

    /**
     * 查询路由角色列表
     *
     * @return 角色标签
     */
    List<ResourceRoleDTO> listResourceRoles();


    /**
     * 查询角色列表
     *
     * @param current     页码
     * @param size        条数
     * @param conditionVO 条件
     * @return 角色列表
     */
    List<RoleDTO> listRoles(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

}




