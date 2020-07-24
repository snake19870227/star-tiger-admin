package com.snake19870227.stiger.admin.common.layui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/22
 */
public class MenuInfo {

    private String title;
    private String icon;
    private String href;
    private String target;
    private List<MenuInfo> child;

    public MenuInfo() {
        this.icon = "fa fa-bars";
        this.href = "";
        this.target = "_self";
        this.child = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<MenuInfo> getChild() {
        return child;
    }

    public void setChild(List<MenuInfo> child) {
        this.child = child;
    }
}
