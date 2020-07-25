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
 * @since 2020-07-25
 */
@ApiModel(value="SysRole对象", description="")
public class SysRole implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "角色流水号")
    @TableId(value = "role_flow", type = IdType.ASSIGN_UUID)
    private String roleFlow;

    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getRoleFlow() {
        return roleFlow;
    }

    public SysRole setRoleFlow(String roleFlow) {
        this.roleFlow = roleFlow;
        return this;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public SysRole setRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public SysRole setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysRole setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysRole{" +
        "roleFlow=" + roleFlow +
        ", roleCode=" + roleCode +
        ", roleName=" + roleName +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
