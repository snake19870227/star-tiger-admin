package com.snake19870227.stiger.admin.common.layui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/22
 */
public class InitInfo {

    private HomeInfo homeInfo;

    private LogoInfo logoInfo;

    private List<MenuInfo> menuInfo;

    public InitInfo() {
        this.menuInfo = new ArrayList<>();
    }

    public HomeInfo getHomeInfo() {
        return homeInfo;
    }

    public void setHomeInfo(HomeInfo homeInfo) {
        this.homeInfo = homeInfo;
    }

    public LogoInfo getLogoInfo() {
        return logoInfo;
    }

    public void setLogoInfo(LogoInfo logoInfo) {
        this.logoInfo = logoInfo;
    }

    public List<MenuInfo> getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(List<MenuInfo> menuInfo) {
        this.menuInfo = menuInfo;
    }
}
