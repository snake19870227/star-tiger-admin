package com.snake19870227.stiger.admin.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/24
 */
public class AdminUser extends User {

    private String shortName;

    public AdminUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AdminUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                     String shortName, boolean accountNonLocked) {
        super(username, password, true, true, true, accountNonLocked, authorities);
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
