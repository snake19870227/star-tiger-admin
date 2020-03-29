package com.snake19870227.stiger.admin;

import cn.hutool.core.util.IdUtil;

import java.util.Collections;

import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.core.StarTigerConstant;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class StarTigerAdminConstant {

    public static final String ROOT_USER_FLOW = "00000000000000000000000000000000";
    public static final String ROOT_USER_NAME = "root";

    public static final String SUPER_ROLE_CODE = "super_admin";

    public enum ResourceMethod {
        /**
         * 所有操作范围
         */
        ALL("ALL", "", "全部"),
        /**
         * 查询
         */
        GET("GET", "GET", "查询"),
        /**
         * 新增
         */
        POST("POST", "POST", "新增"),
        /**
         * 修改
         */
        PUT("PUT", "PUT", "修改"),
        /**
         * 删除
         */
        DELETE("DELETE", "DELETE", "删除");

        private final String code;
        private final String value;
        private final String description;

        ResourceMethod(String code, String value, String description) {
            this.code = code;
            this.value = value;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }

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
