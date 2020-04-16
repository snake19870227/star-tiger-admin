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

    public static class UrlParamNames {
        public static final String LOGIN_ERROR = "error";
        public static final String LOGIN_EXPIRE = "expire";
        public static final String LOGIN_LOCKED = "locked";
    }

    public static class UrlPath {
        public static final String ROOT = "/";
        public static final String INDEX = "/index";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String MAIN = "/main";
        public static final String ACCESS_DENIED = "/accessDenied";
        public static final String ADMINLTE = "/adminlte";
        public static final String MENU_ROUTING = "/routing";

        public static String[] anonymousPaths() {
            return new String[] {
                    ROOT, INDEX, LOGIN, ACCESS_DENIED, ADMINLTE + "/**", "/session/**"
            };
        }

        public static String[] authenticatedPaths() {
            return new String[] {
                    MAIN, MENU_ROUTING
            };
        }
    }

    public static class WebAttrKey {
        public static final String USER_SIDEBAR = "userSidebar";
        public static final String REMEMBER_ME = "rememberMe";
    }
}
