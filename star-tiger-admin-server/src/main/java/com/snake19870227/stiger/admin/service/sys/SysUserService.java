package com.snake19870227.stiger.admin.service.sys;

import java.util.Optional;

import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.dto.SysUserSearcher;
import com.snake19870227.stiger.admin.entity.po.SysUser;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/23
 */
public interface SysUserService {

    Optional<SysUser> getUserByUsername(String username);

    UserInfo loadUserInfoByUsername(String username);

    UserInfo loadUserInfo(String userFlow);

    RecordPage<SysUser> searchUsers(SysUserSearcher searcher, long page, long pageSize);

    SysUser createUser(SysUser user, String[] roleFlows);

    SysUser modifyUser(SysUser user, String[] roleFlows);

    SysUser changeUserLockState(String userFlow, boolean unlocked);

    SysUser resetUserPassword(String userFlow);

    SysUser deleteUser(String userFlow);
}
