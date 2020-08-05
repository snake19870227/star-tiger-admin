package com.snake19870227.stiger.admin.manager.entity.dto;

import com.snake19870227.stiger.admin.entity.po.SysRole;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/08/05
 */
public class RoleResourceDto {

    private SysRole role;

    private String[] resourceFlows;

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public String[] getResourceFlows() {
        return resourceFlows;
    }

    public void setResourceFlows(String[] resourceFlows) {
        this.resourceFlows = resourceFlows;
    }
}
