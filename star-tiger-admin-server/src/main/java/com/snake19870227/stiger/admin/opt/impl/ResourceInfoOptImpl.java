package com.snake19870227.stiger.admin.opt.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snake19870227.stiger.admin.dao.mapper.SysMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysResourceMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleResourceMapper;
import com.snake19870227.stiger.admin.entity.bo.ResourceInfo;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysRoleResource;
import com.snake19870227.stiger.admin.opt.ResourceInfoOpt;

/**
 * @author Bu HuaYang
 */
@Component
public class ResourceInfoOptImpl implements ResourceInfoOpt {

    private final SysMapper sysMapper;
    private final SysResourceMapper sysResourceMapper;
    private final SysRoleResourceMapper sysRoleResourceMapper;
    private final SysRoleMapper sysRoleMapper;

    public ResourceInfoOptImpl(SysMapper sysMapper, SysResourceMapper sysResourceMapper,
                               SysRoleResourceMapper sysRoleResourceMapper,
                               SysRoleMapper sysRoleMapper) {
        this.sysMapper = sysMapper;
        this.sysResourceMapper = sysResourceMapper;
        this.sysRoleResourceMapper = sysRoleResourceMapper;
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    public List<SysResource> getByRoleCode(String roleCode) {
        SysRole role = sysRoleMapper.getByRoleCode(roleCode);
        if (role != null) {
            return sysMapper.selectResourceByRoleFlow(role.getRoleFlow());
        }
        return null;
    }

    @Override
    @Cacheable(cacheNames = "SysResource", key = "'all'")
    public List<SysResource> getAll() {
        QueryWrapper<SysResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("res_name");
        return sysResourceMapper.selectList(queryWrapper);
    }

    @Override
    @Cacheable(cacheNames = "ResourceInfo", key = "#resourceFlow")
    public ResourceInfo loadResourceInfo(String resourceFlow) {
        SysResource resource = sysResourceMapper.selectById(resourceFlow);
        List<SysRoleResource> roleResources = sysRoleResourceMapper.queryByResourceFlow(resourceFlow);
        List<SysRole> roles = roleResources.stream()
                .map(sysRoleResource -> sysRoleMapper.selectById(sysRoleResource.getRoleFlow()))
                .collect(Collectors.toList());
        return new ResourceInfo(resource, roles);
    }
}
