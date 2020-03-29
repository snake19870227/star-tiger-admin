package com.snake19870227.stiger.admin.web;

/**
 * @author Bu HuaYang
 */
public class ProjectConstant {

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
