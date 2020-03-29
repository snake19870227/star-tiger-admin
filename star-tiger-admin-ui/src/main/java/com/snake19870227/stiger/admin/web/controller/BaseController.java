package com.snake19870227.stiger.admin.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.snake19870227.stiger.admin.web.entity.vo.Sidebar;
import com.snake19870227.stiger.admin.web.utils.AdminUiUtil;
import com.snake19870227.stiger.web.exception.MvcException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/17
 */
public class BaseController {

    protected void openMenu(String menuCode, HttpServletRequest request) {
        getUserSidebar(request).open(menuCode);
    }

    protected void closeAllMenu(HttpServletRequest request) {
        getUserSidebar(request).closeAll();
    }

    protected Sidebar getUserSidebar(HttpServletRequest request) {
        Sidebar userSidebar = AdminUiUtil.getSidebar(request);
        if (userSidebar == null) {
            throw new MvcException("无法加载菜单栏");
        }
        return userSidebar;
    }
}
