package com.snake19870227.stiger.admin.entity.bo;

import java.io.Serializable;
import java.util.List;

import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7044096479064862435L;

    private SysUser user;

    private List<SysRole> roles;

    public UserInfo(SysUser user, List<SysRole> roles) {
        this.user = user;
        this.roles = roles;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
}
