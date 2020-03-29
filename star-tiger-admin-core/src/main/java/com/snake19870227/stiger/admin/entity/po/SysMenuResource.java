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
@ApiModel(value="SysMenuResource对象", description="")
public class SysMenuResource implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "菜单资源信息流水号")
    @TableId(value = "menu_res_flow", type = IdType.ASSIGN_UUID)
    private String menuResFlow;

    @ApiModelProperty(value = "菜单流水号")
    private String menuFlow;

    @ApiModelProperty(value = "资源流水号")
    private String resFlow;


    public String getMenuResFlow() {
        return menuResFlow;
    }

    public SysMenuResource setMenuResFlow(String menuResFlow) {
        this.menuResFlow = menuResFlow;
        return this;
    }

    public String getMenuFlow() {
        return menuFlow;
    }

    public SysMenuResource setMenuFlow(String menuFlow) {
        this.menuFlow = menuFlow;
        return this;
    }

    public String getResFlow() {
        return resFlow;
    }

    public SysMenuResource setResFlow(String resFlow) {
        this.resFlow = resFlow;
        return this;
    }

    @Override
    public String toString() {
        return "SysMenuResource{" +
        "menuResFlow=" + menuResFlow +
        ", menuFlow=" + menuFlow +
        ", resFlow=" + resFlow +
        "}";
    }
}
