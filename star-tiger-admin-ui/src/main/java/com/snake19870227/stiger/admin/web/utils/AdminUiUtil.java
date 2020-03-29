package com.snake19870227.stiger.admin.web.utils;

import javax.servlet.http.HttpServletRequest;

import com.snake19870227.stiger.admin.web.ProjectConstant;
import com.snake19870227.stiger.admin.web.entity.vo.Sidebar;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/21
 */
public class AdminUiUtil {

    public static Sidebar getSidebar(HttpServletRequest request) {
        return (Sidebar) request.getSession().getAttribute(ProjectConstant.WebAttrKey.USER_SIDEBAR);
    }
}
