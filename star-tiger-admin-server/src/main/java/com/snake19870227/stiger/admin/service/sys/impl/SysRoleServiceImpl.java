package com.snake19870227.stiger.admin.service.sys.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.snake19870227.stiger.admin.dao.mapper.SysMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleResourceMapper;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.bo.RoleInfo;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysRoleResource;
import com.snake19870227.stiger.admin.opt.RoleInfoOpt;
import com.snake19870227.stiger.admin.service.sys.SysRoleService;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/24
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleResourceMapper sysRoleResourceMapper;

    private final SysMapper sysMapper;

    private final RoleInfoOpt roleInfoOpt;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysRoleResourceMapper sysRoleResourceMapper,
                              SysMapper sysMapper, RoleInfoOpt roleInfoOpt) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysRoleResourceMapper = sysRoleResourceMapper;
        this.sysMapper = sysMapper;
        this.roleInfoOpt = roleInfoOpt;
    }

    @Override
    public Optional<SysRole> readRoleByRoleCode(String roleCode) {
        return Optional.ofNullable(sysRoleMapper.getByRoleCode(roleCode));
    }

    @Override
    public List<SysRole> getAllRoles() {
        return roleInfoOpt.getAll();
    }

    @Override
    public RecordPage<SysRole> searchRoles(String searchCode, String searchName, String searchResName, long page, long pageSize) {
        RecordPage<SysRole> pager = new RecordPage<>(page, pageSize);
        return sysMapper.selectRoles(pager, searchCode, searchName, searchResName);
    }

    @Override
    public RoleInfo readRoleInfo(String roleFlow) {
        return roleInfoOpt.readRoleInfo(roleFlow);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "SysRole", key = "'all'", beforeInvocation = true),
                    @CacheEvict(cacheNames = "SysRole", key = "#roleFlow", beforeInvocation = true),
                    @CacheEvict(cacheNames = "RoleInfo", key = "#roleFlow", beforeInvocation = true),
                    @CacheEvict(cacheNames = "ResourceInfo", allEntries = true, beforeInvocation = true)
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRole(String roleFlow) {
        sysRoleResourceMapper.deleteByRoleFlow(roleFlow);
        return sysRoleMapper.deleteById(roleFlow) == 1;
    }

    private void createRoleResources(SysRole role, String[] resFlows) {
        for (String resFlow : resFlows) {
            SysRoleResource roleResource = new SysRoleResource();
            roleResource.setRoleFlow(role.getRoleFlow())
                    .setResFlow(resFlow);
            sysRoleResourceMapper.insert(roleResource);
        }
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "SysRole", key = "'all'", beforeInvocation = true),
                    @CacheEvict(cacheNames = "ResourceInfo", allEntries = true, beforeInvocation = true)
            },
            put = {
                    @CachePut(cacheNames = "SysRole", key = "#role.roleFlow")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysRole createRole(SysRole role, String[] resFlows) {
        sysRoleMapper.insert(role);
        createRoleResources(role, resFlows);
        return role;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "SysRole", key = "'all'", beforeInvocation = true),
                    @CacheEvict(cacheNames = "RoleInfo", key = "#role.roleFlow", beforeInvocation = true),
                    @CacheEvict(cacheNames = "ResourceInfo", allEntries = true, beforeInvocation = true)
            },
            put = {
                    @CachePut(cacheNames = "SysRole", key = "#role.roleFlow")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysRole modifyRole(SysRole role, String[] resFlows) {
        sysRoleMapper.updateById(role);
        sysRoleResourceMapper.deleteByRoleFlow(role.getRoleFlow());
        createRoleResources(role, resFlows);
        return role;
    }
}
