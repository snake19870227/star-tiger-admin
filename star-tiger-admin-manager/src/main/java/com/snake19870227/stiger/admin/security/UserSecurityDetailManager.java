package com.snake19870227.stiger.admin.security;

import cn.hutool.core.util.StrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snake19870227.stiger.admin.common.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.admin.sys.service.ISysExtService;
import com.snake19870227.stiger.admin.sys.service.ISysUserService;
import com.snake19870227.stiger.admin.util.RabcUtil;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/21
 */
public class UserSecurityDetailManager implements UserDetailsManager {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityDetailManager.class);

    private final ISysUserService sysUserService;

    private final ISysExtService sysExtService;

    public UserSecurityDetailManager(ISysUserService sysUserService,
                                     ISysExtService sysExtService) {
        this.sysUserService = sysUserService;
        this.sysExtService = sysExtService;
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
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return sysUserService.count(wrapper) == 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = null;

        if (StrUtil.equals(username, StarTigerAdminConstant.ROOT_USER_NAME)) {
            userInfo = RabcUtil.getRootUser();
        } else {
            userInfo = sysExtService.getUserInfo(username);
        }

        if (userInfo == null) {
            throw new UsernameNotFoundException(StrUtil.format("未找到用户名[{}]对应的账户", username));
        }

        return new UserSecurityDetail(userInfo);
    }
}
