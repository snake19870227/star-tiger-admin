package com.snake19870227.stiger.admin.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.core.StarTigerConstant;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/21
 */
public class UserSecurityDetail extends UserInfo implements UserDetails {

    private List<SimpleGrantedAuthority> authorities;

    public UserSecurityDetail(UserInfo userInfo) {
        super(userInfo.getUser(), userInfo.getRoles(), userInfo.getResources());
        if (CollUtil.isNotEmpty(userInfo.getRoles())) {
            this.authorities = userInfo.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(StarTigerConstant.SPRING_SECURITY_ROLE_PREFIX + role.getRoleCode()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return getUser().getEncodePassword();
    }

    @Override
    public String getUsername() {
        return getUser().getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return StrUtil.equals(StarTigerConstant.FLAG_N, getUser().getExpired());
    }

    @Override
    public boolean isAccountNonLocked() {
        return StrUtil.equals(StarTigerConstant.FLAG_N, getUser().getLocked());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getShortName() {
        return getUser().getShortName();
    }
}
