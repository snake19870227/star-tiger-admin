package com.snake19870227.stiger.admin.security;

import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.service.sys.SysUserService;
import com.snake19870227.stiger.core.StarTigerConstant;

/**
 * @author Bu HuaYang
 */
public class CustomUserDetailsManager implements UserDetailsManager {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsManager.class);

    private final SysUserService sysUserService;

    public CustomUserDetailsManager(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return sysUserService.getUserByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo rootUserInfo = StarTigerAdminConstant.getRootUser();

        UserInfo userInfo;
        if (StrUtil.equals(username, rootUserInfo.getUser().getUsername())) {
            userInfo = rootUserInfo;
        } else {
            userInfo = sysUserService.loadUserInfoByUsername(username);
        }

        if (userInfo == null) {
            throw new UsernameNotFoundException(StrUtil.format("未找到用户名[{}]对应的账户", username));
        }

        List<GrantedAuthority> roleCodeList = userInfo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(StarTigerConstant.SPRING_SECURITY_ROLE_PREFIX + role.getRoleCode()))
                .collect(Collectors.toList());
        AdminUser adminUser = new AdminUser(
                userInfo.getUser().getUsername(),
                userInfo.getUser().getEncodePassword(),
                roleCodeList.isEmpty() ? AuthorityUtils.NO_AUTHORITIES : roleCodeList,
                userInfo.getUser().getShortName(),
                StrUtil.equals(userInfo.getUser().getLocked(), StarTigerConstant.FLAG_N)
        );
        adminUser.setShortName(userInfo.getUser().getShortName());
        return adminUser;
    }
}
