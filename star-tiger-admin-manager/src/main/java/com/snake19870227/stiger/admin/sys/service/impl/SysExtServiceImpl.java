package com.snake19870227.stiger.admin.sys.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snake19870227.stiger.admin.dao.base.SysUserMapper;
import com.snake19870227.stiger.admin.dao.ext.SysExtMapper;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.admin.sys.service.ISysExtService;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/21
 */
@Service
public class SysExtServiceImpl implements ISysExtService {

    private static final Logger logger = LoggerFactory.getLogger(SysExtServiceImpl.class);

    private final SysUserMapper sysUserMapper;

    private final SysExtMapper sysExtMapper;

    public SysExtServiceImpl(SysUserMapper sysUserMapper,
                             SysExtMapper sysExtMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysExtMapper = sysExtMapper;
    }

    @Override
    public UserInfo getUserInfo(String username) {

        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);

        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);

        if (sysUser == null) {
            return null;
        }

        List<SysRole> sysRoles = sysExtMapper.selectRoleByUser(sysUser.getUserFlow());

        return new UserInfo(sysUser, sysRoles);
    }
}
