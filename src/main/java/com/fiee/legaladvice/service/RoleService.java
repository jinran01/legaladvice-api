package com.fiee.legaladvice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fiee.legaladvice.dto.ResourceRoleDTO;
import com.fiee.legaladvice.dto.RoleDTO;
import com.fiee.legaladvice.entity.Role;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;

import java.util.List;
import java.util.Map;

/**
* @author Fiee
* @description 针对表【tb_role】的数据库操作Service
* @createDate 2024-03-02 19:42:01
*/

public interface RoleService extends IService<Role> {

    List<String> listRolesByUserAuthId(Integer userAuthId);

    List<ResourceRoleDTO> listResourceRoles();

    /**
     * 查询角色
     * @param conditionVO
     * @return
     */
    PageResult<RoleDTO> listRoles(ConditionVO conditionVO);
    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    boolean delRoles(List<Integer> roleIds);
    /**
     * 角色禁用
     * @param role
     * @return
     */
    boolean removeRoles(Role role);

    /**
     * 添加角色
     * @param map
     * @return
     */
    boolean addRole(Map map);

    /**
     * 更新角色菜单
     * @param map
     * @return
     */
    boolean updateMenus(Map map);

    /**
     * 更新角色资源
     * @param map
     * @return
     */
    boolean updateResources(Map map);
}
