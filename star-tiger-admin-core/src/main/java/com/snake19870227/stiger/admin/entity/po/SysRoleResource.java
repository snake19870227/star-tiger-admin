package com.snake19870227.stiger.admin.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author buhuayang
 * @since 2020-04-02
 */
@ApiModel(value="SysRoleResource对象", description="")
public class SysRoleResource implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "角色资源信息流水号")
    @TableId(value = "role_res_flow", type = IdType.ASSIGN_UUID)
    private String roleResFlow;

    @ApiModelProperty(value = "角色流水号")
    private String roleFlow;

    @ApiModelProperty(value = "资源流水号")
    private String resFlow;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getRoleResFlow() {
        return roleResFlow;
    }

    public SysRoleResource setRoleResFlow(String roleResFlow) {
        this.roleResFlow = roleResFlow;
        return this;
    }

    public String getRoleFlow() {
        return roleFlow;
    }

    public SysRoleResource setRoleFlow(String roleFlow) {
        this.roleFlow = roleFlow;
        return this;
    }

    public String getResFlow() {
        return resFlow;
    }

    public SysRoleResource setResFlow(String resFlow) {
        this.resFlow = resFlow;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysRoleResource setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysRoleResource{" +
        "roleResFlow=" + roleResFlow +
        ", roleFlow=" + roleFlow +
        ", resFlow=" + resFlow +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
