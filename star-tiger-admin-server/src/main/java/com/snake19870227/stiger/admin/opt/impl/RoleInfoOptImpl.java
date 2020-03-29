package com.snake19870227.stiger.admin.opt.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snake19870227.stiger.admin.dao.mapper.SysMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysResourceMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleMapper;
import com.snake19870227.stiger.admin.entity.bo.RoleInfo;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.opt.RoleInfoOpt;
import com.snake19870227.stiger.core.exception.OptException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/20
 */
@Component
public class RoleInfoOptImpl implements RoleInfoOpt {

    private static final Logger logger = LoggerFactory.getLogger(RoleInfoOptImpl.class);

    private final SysResourceMapper sysResourceMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMapper sysMapper;

    public RoleInfoOptImpl(SysResourceMapper sysResourceMapper, SysRoleMapper sysRoleMapper, SysMapper sysMapper) {
        this.sysResourceMapper = sysResourceMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysMapper = sysMapper;
    }

    @Override
    @Cacheable(cacheNames = "SysRole", key = "'all'")
    public List<SysRole> getAll() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("role_name");
        return sysRoleMapper.selectList(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = "RoleInfo", key = "#roleFlow")
    public RoleInfo readRoleInfo(String roleFlow) {
        SysRole role = sysRoleMapper.selectById(roleFlow);
        if (role == null) {
            throw new OptException("角色不存在");
        }

        List<SysResource> resources = sysMapper.selectResourceByRoleFlow(role.getRoleFlow());

        return new RoleInfo(role, resources);
    }
}
