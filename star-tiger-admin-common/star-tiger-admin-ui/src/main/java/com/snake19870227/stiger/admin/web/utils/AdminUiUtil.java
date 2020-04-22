package com.snake19870227.stiger.admin.web.utils;

import javax.servlet.http.HttpServletRequest;

import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.web.entity.vo.Sidebar;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/21
 */
public class AdminUiUtil {

    public static Sidebar getSidebar(HttpServletRequest request) {
        return (Sidebar) request.getSession().getAttribute(StarTigerAdminConstant.WebAttrKey.USER_SIDEBAR);
    }
}
