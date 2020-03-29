package com.snake19870227.stiger.admin.entity.bo;

import java.io.Serializable;
import java.util.List;

import com.snake19870227.stiger.admin.entity.po.SysMenu;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class MenuInfo implements Serializable {

    private static final long serialVersionUID = 5906174533642162280L;

    private SysMenu menu;

    private List<SysMenu> childMenus;

    public MenuInfo(SysMenu menu, List<SysMenu> childMenus) {
        this.menu = menu;
        this.childMenus = childMenus;
    }

    public SysMenu getMenu() {
        return menu;
    }

    public void setMenu(SysMenu menu) {
        this.menu = menu;
    }

    public List<SysMenu> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<SysMenu> childMenus) {
        this.childMenus = childMenus;
    }
}
