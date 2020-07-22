package com.snake19870227.stiger.admin.entity.bo;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/16
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -7044096479064862435L;

    private SysUser user;

    private List<SysRole> roles;

    private List<SysResource> resources;

    private List<AntPathRequestMatcher> matchers;

    public UserInfo() {
    }

    public UserInfo(SysUser user, List<SysRole> roles, List<SysResource> resources) {
        this.user = user;
        this.roles = roles;
        this.resources = resources;
        this.matchers = new ArrayList<>();
        resources.forEach(
                resource -> matchers.add(new AntPathRequestMatcher(resource.getResPath(), resource.getResMethod()))
        );
    }

    public boolean canAccess(HttpServletRequest request) {
        for (AntPathRequestMatcher matcher : this.matchers) {
            boolean flag = matcher.matches(request);
            if (flag) {
                return true;
            }
        }
        return false;
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

    public List<SysResource> getResources() {
        return resources;
    }

    public void setResources(List<SysResource> resources) {
        this.resources = resources;
    }

    public List<AntPathRequestMatcher> getMatchers() {
        return matchers;
    }

    public void setMatchers(List<AntPathRequestMatcher> matchers) {
        this.matchers = matchers;
    }
}
