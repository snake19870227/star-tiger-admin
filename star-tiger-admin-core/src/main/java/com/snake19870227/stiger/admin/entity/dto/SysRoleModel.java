package com.snake19870227.stiger.admin.entity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/26
 */
public class SysRoleModel {

    private String roleFlow;

    @NotNull
    @Size(max = 20)
    private String roleCode;

    @NotNull
    @Size(max = 20)
    private String roleName;

    public String getRoleFlow() {
        return roleFlow;
    }

    public void setRoleFlow(String roleFlow) {
        this.roleFlow = roleFlow;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
