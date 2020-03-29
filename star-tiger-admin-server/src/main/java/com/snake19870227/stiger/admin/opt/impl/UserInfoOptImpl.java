package com.snake19870227.stiger.admin.opt.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.snake19870227.stiger.admin.dao.mapper.SysResourceMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleResourceMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysUserMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysUserRoleMapper;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.admin.entity.po.SysUserRole;
import com.snake19870227.stiger.admin.opt.UserInfoOpt;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
@Component
public class UserInfoOptImpl implements UserInfoOpt {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoOptImpl.class);

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysResourceMapper sysResourceMapper;
    private final SysRoleResourceMapper sysRoleResourceMapper;

    public UserInfoOptImpl(SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper,
                           SysUserRoleMapper sysUserRoleMapper, SysResourceMapper sysResourceMapper,
                           SysRoleResourceMapper sysRoleResourceMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysResourceMapper = sysResourceMapper;
        this.sysRoleResourceMapper = sysRoleResourceMapper;
    }

    @Override
    @Cacheable(cacheNames = "UserInfo", key = "#userFlow")
    public UserInfo loadUserInfo(String userFlow) {
        SysUser user = sysUserMapper.selectById(userFlow);
        List<SysRole> roles = getRoles(user.getUserFlow());
        return new UserInfo(user, roles);
    }

    @Override
    @Cacheable(cacheNames = "UserInfo", key = "#user.userFlow")
    public UserInfo loadUserInfo(SysUser user) {
        List<SysRole> roles = getRoles(user.getUserFlow());
        return new UserInfo(user, roles);
    }

    private List<SysRole> getRoles(String userFlow) {
        List<SysUserRole> userRoles = sysUserRoleMapper.queryByUserFlow(userFlow);
        return userRoles.stream()
                .map(sysUserRole -> sysRoleMapper.selectById(sysUserRole.getRoleFlow()))
                .collect(Collectors.toList());
    }
}
