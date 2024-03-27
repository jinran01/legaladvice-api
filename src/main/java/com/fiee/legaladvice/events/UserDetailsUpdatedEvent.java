package com.fiee.legaladvice.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * @Author: Fiee
 * @ClassName: com.fiee.legaladvice.events
 * @Date: 2024/3/24
 * @Version: v1.0.0
 **/
public class UserDetailsUpdatedEvent extends ApplicationEvent {
    private final UserDetails updatedUserDetails;

    public UserDetailsUpdatedEvent(Object source, UserDetails updatedUserDetails) {
        super(source);
        this.updatedUserDetails = updatedUserDetails;
    }

    public UserDetails getUpdatedUserDetails() {
        return updatedUserDetails;
    }

}
