package com.snake19870227.stiger.admin.service.sys;

import java.util.List;
import java.util.Map;

import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/02
 */
public interface SysMenuService {

    SysMenu getByMenuFlow(String menuFlow);

    SysMenu getMenuByMenuCode(String menuCode);

    List<MenuInfo> allMenuTree();

    SysMenu create(SysMenu menu);

    SysMenu modify(SysMenu menu);

    SysMenu delete(String menuFlow);

    SysMenu swapOrder(String menuFlow1, String menuFlow2);
}
