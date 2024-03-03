package com.fiee.legaladvice.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.fiee.legaladvice.dto.ResourceRoleDTO;
import com.fiee.legaladvice.dto.RoleDTO;
import com.fiee.legaladvice.entity.Role;
import com.fiee.legaladvice.entity.RoleMenu;
import com.fiee.legaladvice.entity.RoleResource;
import com.fiee.legaladvice.entity.UserRole;
import com.fiee.legaladvice.exception.BizException;
import com.fiee.legaladvice.service.RoleMenuService;
import com.fiee.legaladvice.service.RoleResourceService;
import com.fiee.legaladvice.service.RoleService;
import com.fiee.legaladvice.mapper.RoleMapper;
import com.fiee.legaladvice.service.UserRoleService;
import com.fiee.legaladvice.vo.ConditionVO;
import com.fiee.legaladvice.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author Fiee
* @description 针对表【tb_role】的数据库操作Service实现
* @createDate 2024-03-02 19:42:01
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private RoleMenuService roleMenuService;
    /**
     * 用户角色
     * @return
     */
    @Override
    public List<String> listRolesByUserInfoId(Integer userInfoId) {
        return baseMapper.listRolesByUserInfoId(userInfoId);
    }

    /**
     * 角色资源
     * @return
     */
    @Override
    public List<ResourceRoleDTO> listResourceRoles() {
        return baseMapper.listResourceRoles();
    }

    /**
     * 查询角色
     * @param condition
     * @return
     */
    @Override
    public PageResult<RoleDTO> listRoles(ConditionVO condition) {
        Integer count = Math.toIntExact(baseMapper.selectCount(null));
        List<RoleDTO> list = baseMapper.listRoles((condition.getCurrent() - 1) *condition.getSize(), condition.getSize(), condition);
        return new PageResult<>(list, count);
    }

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    @Override
    @Transactional
    public boolean delRoles(List<Integer> roleIds) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        for (Integer id:roleIds) {
            wrapper.eq(UserRole::getRoleId, id);
            //判断这些角色下是否有用户
            if (userRoleService.list(wrapper).size() > 0){
                throw new BizException("该角色下有用户");
            }else {
                //删除该角色下的资源
                LambdaQueryWrapper<RoleResource> resourceWrapper = new LambdaQueryWrapper<>();
                resourceWrapper.eq(RoleResource::getRoleId,id);
                roleResourceService.remove(resourceWrapper);
                //删除该角色下的菜单
                LambdaQueryWrapper<RoleMenu> menuWrapper = new LambdaQueryWrapper<>();
                menuWrapper.eq(RoleMenu::getRoleId,id);
                roleMenuService.remove(menuWrapper);
            }
        }
        //删除角色
        baseMapper.deleteBatchIds(roleIds);
        return true;
    }

    /**
     * 禁用角色
     * @param role
     */
    @Override
    public boolean removeRoles(Role role) {
        System.out.println(role);
        //查询该角色下是否有用户
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getRoleId,role.getId());
        Long count = userRoleService.count(wrapper);
        if (count > 0){
            return false;
        }else if (count < 0){
            return false;
        }else {
            Role obj = baseMapper.selectById(role.getId());
            if (obj.getIsDisable().intValue() == 0){
                obj.setIsDisable(1);
                obj.setUpdateTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
            }else {
                obj.setIsDisable(0);
            }
            baseMapper.updateById(obj);
            obj.setUpdateTime(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
            return true;
        }
    }

    /**
     * 添加角色
     * @param map
     * @return
     */

    @Override
    @Transactional
    public boolean addRole(Map map) {

        Role role = JSON.parseObject(
                JSON.toJSONString(map.get("role")),
                Role.class);
        boolean flag;
        flag = baseMapper.insert(role) > 0 ? true : false;
//        //如果没插入成功
        if (!flag){
            throw new BizException("出错了");
        }
        //获取插入成功的角色id
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getRoleName,role.getRoleName());
        Integer id = baseMapper.selectOne(wrapper).getId();

        //获取插入的菜单id
        List menuIds = (List) map.get("menuIds");
        List<RoleMenu> list = new ArrayList<>();
        for (Object menuId:menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(id);
            roleMenu.setMenuId((Integer) menuId);
            list.add(roleMenu);
        }
        flag = roleMenuService.saveBatch(list);
        if (!flag){
            throw new BizException("出错了！");
        }
        return flag;
    }

    @Override
    @Transactional
    public boolean updateMenus(Map map) {
        Object roleId = map.get("roleId");
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,roleId);
        roleMenuService.remove(wrapper);

        List menuIds = (List) map.get("resourceIds");
        List<RoleMenu> list = new ArrayList<>();
        for (Object menuId:menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId((Integer) roleId);
            roleMenu.setMenuId((Integer) menuId);
            list.add(roleMenu);
        }
//        throw new BizException("出错了");
        roleMenuService.saveBatch(list);
        return true;
    }

    @Override
    public boolean updateResources(Map map) {
        Object roleId = map.get("roleId");
        LambdaQueryWrapper<RoleResource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleResource::getRoleId,roleId);
        roleResourceService.remove(wrapper);

        List resourceIds = (List) map.get("resourceIds");
        List<RoleResource> list = new ArrayList<>();
        for (Object resourceId:resourceIds) {
            RoleResource resource = new RoleResource();
            resource.setRoleId((Integer) roleId);
            resource.setResourceId((Integer) resourceId);
            list.add(resource);
        }
//        throw new BizException("出错了");
        roleResourceService.saveBatch(list);
        return true;
    }
}




