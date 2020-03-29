package com.snake19870227.stiger.admin.web.service;

import com.snake19870227.stiger.admin.entity.po.SysMenu;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/17
 */
public interface RouterService {

    SysMenu getRouterMenu(String menuCode);
}
