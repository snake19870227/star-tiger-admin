package com.snake19870227.stiger.admin.utils;

import cn.hutool.core.util.IdUtil;

import java.util.Collections;

import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.core.StarTigerConstant;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/18
 */
public class SecurityUtil {

    public static SysRole getSuperRole() {
        SysRole superRole = new SysRole();
        superRole.setRoleFlow(IdUtil.simpleUUID());
        superRole.setRoleCode(StarTigerAdminConstant.SUPER_ROLE_CODE);
        superRole.setRoleName("超级管理员");
        return superRole;
    }

    public static UserInfo getRootUser() {
        SysUser rootUser = new SysUser();
        rootUser.setUserFlow(StarTigerAdminConstant.ROOT_USER_FLOW)
                .setUsername(StarTigerAdminConstant.ROOT_USER_NAME)
                .setEncodePassword("{noop}123456")
                .setShortName("超级管理员")
                .setLocked(StarTigerConstant.FLAG_N);
        return new UserInfo(rootUser, Collections.singletonList(getSuperRole()));
    }
}
