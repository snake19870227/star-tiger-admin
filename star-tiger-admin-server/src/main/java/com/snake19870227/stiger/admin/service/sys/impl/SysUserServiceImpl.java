package com.snake19870227.stiger.admin.service.sys.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.snake19870227.stiger.admin.dao.mapper.SysMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysUserMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysUserRoleMapper;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.dto.SysUserSearcher;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.admin.entity.po.SysUserRole;
import com.snake19870227.stiger.admin.opt.UserInfoOpt;
import com.snake19870227.stiger.admin.service.sys.SysUserService;
import com.snake19870227.stiger.core.StarTigerConstant;
import com.snake19870227.stiger.core.exception.ServiceException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/23
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    private static final String DEFAULT_PASSWORD = "888888";

    private final PasswordEncoder passwordEncoder;

    private final SysMapper sysMapper;

    private final SysUserMapper sysUserMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final UserInfoOpt userInfoOpt;

    public SysUserServiceImpl(PasswordEncoder passwordEncoder, SysMapper sysMapper, SysUserMapper sysUserMapper,
                              SysUserRoleMapper sysUserRoleMapper, UserInfoOpt userInfoOpt) {
        this.passwordEncoder = passwordEncoder;
        this.sysMapper = sysMapper;
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.userInfoOpt = userInfoOpt;
    }

    @Override
    public Optional<SysUser> getUserByUsername(String username) {
        return sysUserMapper.queryByUsername(username);
    }

    @Override
    public UserInfo loadUserInfoByUsername(String username) {
        Optional<SysUser> userObj = sysUserMapper.queryByUsername(username);
        return userObj.map(userInfoOpt::loadUserInfo).orElse(null);
    }

    @Override
    public UserInfo loadUserInfo(String userFlow) {
        return userInfoOpt.loadUserInfo(userFlow);
    }

    @Override
    public RecordPage<SysUser> searchUsers(SysUserSearcher searcher, long page, long pageSize) {
        RecordPage<SysUser> pager = new RecordPage<>(page, pageSize);
        return sysMapper.selectUsers(pager, searcher);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "UserInfo", key = "#user.userFlow")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysUser createUser(SysUser user, String[] roleFlows) {
        user.setEncodePassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        sysUserMapper.insert(user);
        createUserRoles(user, roleFlows);
        return user;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "UserInfo", key = "#user.userFlow", beforeInvocation = true)
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysUser modifyUser(SysUser user, String[] roleFlows) {
        sysUserMapper.updateById(user);
        sysUserRoleMapper.deleteByUserFlow(user.getUserFlow());
        createUserRoles(user, roleFlows);
        return user;
    }

    private void createUserRoles(SysUser user, String[] roleFlows) {
        for (String roleFlow : roleFlows) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleFlow(roleFlow)
                    .setUserFlow(user.getUserFlow());
            sysUserRoleMapper.insert(userRole);
        }
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "UserInfo", key = "#userFlow", beforeInvocation = true)
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysUser changeUserLockState(String userFlow, boolean unlocked) {
        SysUser user = sysUserMapper.selectById(userFlow);
        user.setLocked(unlocked ? StarTigerConstant.FLAG_N : StarTigerConstant.FLAG_Y);
        return sysUserMapper.updateById(user) == 1 ? user : null;
    }

    @Override
    public SysUser resetUserPassword(String userFlow) {
        SysUser user = sysUserMapper.selectById(userFlow);
        user.setEncodePassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        return sysUserMapper.updateById(user) == 1 ? user : null;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "UserInfo", key = "#userFlow", beforeInvocation = true)
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysUser deleteUser(String userFlow) {
        SysUser user = sysUserMapper.selectById(userFlow);
        if (user == null) {
            throw new ServiceException("账户不存在");
        }
        sysUserRoleMapper.deleteByUserFlow(userFlow);
        return sysUserMapper.deleteById(userFlow) == 1 ? user : null;
    }
}
