package com.snake19870227.stiger.admin.entity.bo;

import java.io.Serializable;
import java.util.List;

import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class ResourceInfo implements Serializable {

    private static final long serialVersionUID = 8541645321177363L;

    private SysResource resource;

    private List<SysRole> roles;

    public ResourceInfo(SysResource resource, List<SysRole> roles) {
        this.resource = resource;
        this.roles = roles;
    }

    public SysResource getResource() {
        return resource;
    }

    public void setResource(SysResource resource) {
        this.resource = resource;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
