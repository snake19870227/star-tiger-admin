package com.snake19870227.stiger.admin.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import com.snake19870227.stiger.admin.web.entity.vo.Sidebar;
import com.snake19870227.stiger.admin.web.utils.AdminUiUtil;
import com.snake19870227.stiger.web.exception.PostWebErrorHandler;
import com.snake19870227.stiger.web.utils.WebUtil;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/21
 */
public class WebPostWebErrorHandler implements PostWebErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebPostWebErrorHandler.class);

    @Override
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex, ModelAndView mv) {
        closeAllMenu(request);
    }

    @Override
    public void errorPageHandler(HttpServletRequest request, HttpStatus status, ModelAndView mv) {
        closeAllMenu(request);
    }

    private void closeAllMenu(HttpServletRequest request) {
        if (WebUtil.isAjaxRequest(request)) {
            return;
        }
        Sidebar userSidebar = getSidebar(request);
        if (userSidebar != null) {
            userSidebar.closeAll();
        }
    }

    private Sidebar getSidebar(HttpServletRequest request) {
        return AdminUiUtil.getSidebar(request);
    }
}
