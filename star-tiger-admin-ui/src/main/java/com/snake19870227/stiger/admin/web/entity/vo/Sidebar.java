package com.snake19870227.stiger.admin.web.entity.vo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.web.exception.MvcException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class Sidebar implements Serializable {

    private static final long serialVersionUID = 5807932254815509563L;

    private List<MenuItem> currentMenuPathQueue;

    private List<MenuItem> items;

    public Sidebar(List<MenuInfo> infos) {
        this(null, infos);
    }

    public Sidebar(String currentMenuCode, List<MenuInfo> infos) {
        this.currentMenuPathQueue = new ArrayList<>();
        this.items = new ArrayList<>();
        this.items.addAll(
                infos.stream().map(menuInfo -> {
                    SysMenu menu = menuInfo.getMenu();
                    List<SysMenu> childMenus = menuInfo.getChildMenus();
                    if (CollUtil.isNotEmpty(childMenus)) {
                        final boolean[] isOpen = {false};
                        List<MenuItem> childItems = childMenus.stream().map(sysMenu -> {
                            isOpen[0] = StrUtil.equals(currentMenuCode, sysMenu.getMenuCode());
                            return new MenuItem(false, isOpen[0], sysMenu, null);
                        }).collect(Collectors.toList());
                        return new MenuItem(true, isOpen[0], menu, childItems);
                    } else {
                        return new MenuItem(false, false, menu, null);
                    }
                }).collect(Collectors.toList())
        );
    }

    public void open(String menuCode) {
        this.currentMenuPathQueue.clear();
        this.items.forEach(parentMenu -> {
            if (parentMenu.getChildItems() != null) {
                parentMenu.setActive(false);
                parentMenu.getChildItems().forEach(childMenu -> {
                    if (StrUtil.equals(menuCode, childMenu.getMenu().getMenuCode())) {
                        parentMenu.setActive(true);
                        childMenu.setActive(true);
                        this.currentMenuPathQueue.addAll(Arrays.asList(parentMenu, childMenu));
                    } else {
                        childMenu.setActive(false);
                    }
                });
            }
        });
        if (currentMenuPathQueue.isEmpty()) {
            throw new MvcException("未找到或未被授权访问该功能[menuCode=" + menuCode + "]");
        }
    }

    public void closeAll() {
        this.currentMenuPathQueue.clear();
        this.items.forEach(menuItem -> {
            menuItem.setActive(false);
            if (menuItem.getChildItems() != null) {
                menuItem.getChildItems().forEach(menuItem1 -> menuItem1.setActive(false));
            }
        });
    }

    public List<MenuItem> getCurrentMenuPathQueue() {
        return currentMenuPathQueue;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public static class MenuItem implements Serializable {

        private static final long serialVersionUID = 6573615797428070461L;

        private boolean hasChild;
        private boolean active;

        private SysMenu menu;

        private List<MenuItem> childItems;

        public MenuItem(boolean hasChild, boolean active, SysMenu menu, List<MenuItem> childItems) {
            this.hasChild = hasChild;
            this.active = active;
            this.menu = menu;
            this.childItems = childItems;
        }

        public boolean isHasChild() {
            return hasChild;
        }

        public void setHasChild(boolean hasChild) {
            this.hasChild = hasChild;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public SysMenu getMenu() {
            return menu;
        }

        public void setMenu(SysMenu menu) {
            this.menu = menu;
        }

        public List<MenuItem> getChildItems() {
            return childItems;
        }

        public void setChildItems(List<MenuItem> childItems) {
            this.childItems = childItems;
        }
    }
}
