package com.fiee.legaladvice.listener;

import com.fiee.legaladvice.events.UserDetailsUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.listener
 * @Date: 2024/3/24
 * @Version: v1.0.0
 **/

@Component
public class UserDetailsUpdatedEventListener {
//    @Autowired
//    private SessionRegistry sessionRegistry;

//    @EventListener
//    public void onUserDetailsUpdated(UserDetailsUpdatedEvent event) {
//        UserDetails updatedUserDetails = event.getUpdatedUserDetails();
//        String username = updatedUserDetails.getUsername();
//
//        // 获取当前用户的Authentication对象
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getName().equals(username)) {
//            // 如果当前用户的Authentication已经过期，需要重新认证
//            SecurityContextHolder.getContext().setAuthentication(null);
//        }
//        for (Object principal : sessionRegistry.getAllPrincipals()) {
//            if (principal instanceof String && principal.equals(username)) {
//                for (Object sessionId : sessionRegistry.getAllSessions(principal,true)) {
//                    // 获取会话关联的SecurityContext
//                    SecurityContext securityContext = sessionRegistry.getSecurityContext(sessionId);
//                    if (securityContext != null && securityContext.getAuthentication() != null) {
//                        // 从UserDetailsService重新加载更新后的用户信息
//                        UserDetails updatedUserDetails = event.getUserDetailsService().loadUserByUsername(username);
//
//                        // 创建新的认证信息，并更新SecurityContext
//                        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
//                                updatedUserDetails,
//                                securityContext.getAuthentication().getCredentials(),
//                                securityContext.getAuthentication().getAuthorities()
//                        );
//
//                        // 设置新的认证信息到SecurityContext
//                        securityContext.setAuthentication(newAuthentication);
//                    }
//                }
//            }
//        }
//    }
}