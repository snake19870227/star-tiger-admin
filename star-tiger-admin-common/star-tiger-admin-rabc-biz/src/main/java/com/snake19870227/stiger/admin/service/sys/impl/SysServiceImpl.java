package com.snake19870227.stiger.admin.service.sys.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.snake19870227.stiger.admin.dao.mapper.SysMenuMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysResourceMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysRoleResourceMapper;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.bo.ResourceInfo;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.opt.sys.MenuInfoOpt;
import com.snake19870227.stiger.admin.opt.sys.ResourceInfoOpt;
import com.snake19870227.stiger.admin.service.sys.SysService;
import com.snake19870227.stiger.core.exception.ServiceException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/16
 */
@Service
public class SysServiceImpl implements SysService {

    private static final Logger logger = LoggerFactory.getLogger(SysServiceImpl.class);

    private final SysResourceMapper sysResourceMapper;
    private final SysRoleResourceMapper sysRoleResourceMapper;
    private final SysMenuMapper sysMenuMapper;

    private final ResourceInfoOpt resourceInfoOpt;
    private final MenuInfoOpt menuInfoOpt;

    public SysServiceImpl(SysResourceMapper sysResourceMapper, SysRoleResourceMapper sysRoleResourceMapper,
                          SysMenuMapper sysMenuMapper, ResourceInfoOpt resourceInfoOpt, MenuInfoOpt menuInfoOpt) {
        this.sysResourceMapper = sysResourceMapper;
        this.sysRoleResourceMapper = sysRoleResourceMapper;
        this.sysMenuMapper = sysMenuMapper;
        this.resourceInfoOpt = resourceInfoOpt;
        this.menuInfoOpt = menuInfoOpt;
    }

    /* ====================< Resource >==================== */

    @Override
    public List<SysResource> getAllResource() {
        return resourceInfoOpt.getAll();
    }

    @Override
    public ResourceInfo loadResourceInfo(String resourceFlow) {
        return resourceInfoOpt.loadResourceInfo(resourceFlow);
    }

    @Override
    public List<SysResource> getResourceByRoleCode(String roleCode) {
        return resourceInfoOpt.getByRoleCode(roleCode);
    }

    @Override
    public RecordPage<SysResource> searchResources(String resName, long page, long pageSize) {
        RecordPage<SysResource> pager = new RecordPage<>(page, pageSize);
        return sysResourceMapper.get(pager, resName);
    }

    @Override
    @Cacheable(cacheNames = "SysResource", key = "#resFlow")
    public SysResource readResource(String resFlow) {
        return sysResourceMapper.selectById(resFlow);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "SysResource", key = "'all'", beforeInvocation = true)
            },
            put = {
                    @CachePut(cacheNames = "SysResource", key = "#resource.resFlow")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysResource createResource(SysResource resource) {
        if (sysResourceMapper.insert(resource) != 1) {
            throw new ServiceException("新增资源失败");
        }
        return resource;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "SysResource", key = "'all'", beforeInvocation = true),
                    @CacheEvict(cacheNames = "ResourceInfo", key = "#resource.resFlow", beforeInvocation = true)
            },
            put = {
                    @CachePut(cacheNames = "SysResource", key = "#resource.resFlow")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysResource modifyResource(SysResource resource) {
        if (resource.getResMethod() == null) {
            resource.setResMethod("");
        }
        if (sysResourceMapper.updateById(resource) != 1) {
            throw new ServiceException("修改资源失败");
        }
        return resource;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "SysResource", key = "'all'", beforeInvocation = true),
                    @CacheEvict(cacheNames = "SysResource", key = "#resFlow", beforeInvocation = true),
                    @CacheEvict(cacheNames = "ResourceInfo", key = "#resFlow", beforeInvocation = true),
                    @CacheEvict(cacheNames = "RoleInfo", allEntries = true, beforeInvocation = true)
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteResource(String resFlow) {
        sysRoleResourceMapper.deleteByResFlow(resFlow);
        return sysResourceMapper.deleteById(resFlow) == 1;
    }

    /* ====================< Role >==================== */

    /* ====================< User >==================== */

    /* ====================< Menu >==================== */
}
