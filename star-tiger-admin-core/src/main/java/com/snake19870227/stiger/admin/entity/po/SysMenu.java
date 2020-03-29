package com.snake19870227.stiger.admin.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author buhuayang
 * @since 2020-03-27
 */
@ApiModel(value="SysMenu对象", description="")
public class SysMenu implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "菜单流水号")
    @TableId(value = "menu_flow", type = IdType.ASSIGN_UUID)
    private String menuFlow;

    @ApiModelProperty(value = "父级菜单流水号")
    private String parentMenuFlow;

    @ApiModelProperty(value = "菜单层级")
    private Integer menuLevel;

    @ApiModelProperty(value = "菜单代码")
    private String menuCode;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单地址")
    private String menuPath;

    @ApiModelProperty(value = "排序码")
    private Integer menuOrder;


    public String getMenuFlow() {
        return menuFlow;
    }

    public SysMenu setMenuFlow(String menuFlow) {
        this.menuFlow = menuFlow;
        return this;
    }

    public String getParentMenuFlow() {
        return parentMenuFlow;
    }

    public SysMenu setParentMenuFlow(String parentMenuFlow) {
        this.parentMenuFlow = parentMenuFlow;
        return this;
    }

    public Integer getMenuLevel() {
        return menuLevel;
    }

    public SysMenu setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
        return this;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public SysMenu setMenuCode(String menuCode) {
        this.menuCode = menuCode;
        return this;
    }

    public String getMenuName() {
        return menuName;
    }

    public SysMenu setMenuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public SysMenu setMenuPath(String menuPath) {
        this.menuPath = menuPath;
        return this;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public SysMenu setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
        return this;
    }

    @Override
    public String toString() {
        return "SysMenu{" +
        "menuFlow=" + menuFlow +
        ", parentMenuFlow=" + parentMenuFlow +
        ", menuLevel=" + menuLevel +
        ", menuCode=" + menuCode +
        ", menuName=" + menuName +
        ", menuPath=" + menuPath +
        ", menuOrder=" + menuOrder +
        "}";
    }
}
