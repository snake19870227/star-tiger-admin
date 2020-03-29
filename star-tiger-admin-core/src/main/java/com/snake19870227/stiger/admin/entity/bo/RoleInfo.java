package com.snake19870227.stiger.admin.entity.bo;

import java.io.Serializable;
import java.util.List;

import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 8313164172229230061L;

    private SysRole role;

    private List<SysResource> resources;

    public RoleInfo(SysRole role, List<SysResource> resources) {
        this.role = role;
        this.resources = resources;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public List<SysResource> getResources() {
        return resources;
    }

    public void setResources(List<SysResource> resources) {
        this.resources = resources;
    }
}
