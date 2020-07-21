package com.snake19870227.stiger.admin.common;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/16
 */
public class StarTigerAdminConstant {

    public static final String ROOT_USER_FLOW = "00000000000000000000000000000000";
    public static final String ROOT_USER_NAME = "root";

    public static final String SUPER_ROLE_CODE = "super_admin";

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

        public static final String OAUTH_PATTERN = "/oauth/**";
        public static final String ACTUATOR_PATTERN = "/actuator/**";

        public static String[] anonymousPaths() {
            return new String[] {
                    ROOT, INDEX, LOGIN, ACCESS_DENIED, "/jquery/**", "/layui/**"
            };
        }

        public static String[] authenticatedPaths() {
            return new String[] {
                    MAIN, MENU_ROUTING, ACTUATOR_PATTERN
            };
        }
    }

    public static class WebAttrKey {
        public static final String USER_SIDEBAR = "userSidebar";
    }
}
