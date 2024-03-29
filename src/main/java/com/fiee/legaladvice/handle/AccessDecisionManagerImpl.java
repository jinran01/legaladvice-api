package com.fiee.legaladvice.handle;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Fiee
 * @ClassName: AccessDecisionManagerImpl
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Component
public class AccessDecisionManagerImpl implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication,
                       Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        //TODO 问题：如果只判断标签的话会出现BUG 例如有相同的标签的话会被放行
        //TODO 优化：应该判断角色ID 或者不能添加相同的标签

        // 获取用户权限列表
        List<String> permissionList = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        for (ConfigAttribute item : configAttributes) {
            if (permissionList.contains(item.getAttribute())) {
                return;
            }
        }
        throw new AccessDeniedException("没有操作权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
