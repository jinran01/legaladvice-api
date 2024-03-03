package com.fiee.legaladvice.utils;

import com.fiee.legaladvice.dto.UserDetailDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.utils
 * @Date: 2024/3/2
 * @Version: v1.0.0
 **/
@Component
public class UserUtils {

    /**
     * 获取当前登录用户
     *
     * @return 用户登录信息
     */
    public static UserDetailDTO getLoginUser() {
        return (UserDetailDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}