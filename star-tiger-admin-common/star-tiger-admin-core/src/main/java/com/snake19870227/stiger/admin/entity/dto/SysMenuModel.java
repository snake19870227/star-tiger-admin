package com.snake19870227.stiger.admin.entity.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/02
 */
public class SysMenuModel {

    private String menuFlow;

    private String parentMenuFlow;

    @NotNull
    @Min(1)
    @Max(2)
    @Digits(integer = 1, fraction = 0)
    private Integer menuLevel;

    @NotNull
    @Size(max = 20)
    private String menuCode;

    @NotNull
    @Size(max = 20)
    private String menuName;

    private String menuPath;

    public String getMenuFlow() {
        return menuFlow;
    }

    public void setMenuFlow(String menuFlow) {
        this.menuFlow = menuFlow;
    }

    public String getParentMenuFlow() {
        return parentMenuFlow;
    }

    public void setParentMenuFlow(String parentMenuFlow) {
        this.parentMenuFlow = parentMenuFlow;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }
}
