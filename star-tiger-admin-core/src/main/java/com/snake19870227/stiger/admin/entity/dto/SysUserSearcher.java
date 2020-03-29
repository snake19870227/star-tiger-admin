package com.snake19870227.stiger.admin.entity.dto;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/22
 */
public class SysUserSearcher {

    private String searchUsername;
    private String searchShortName;
    private String locked;
    private String[] searchUserRoleFlows;

    public String getSearchUsername() {
        return searchUsername;
    }

    public void setSearchUsername(String searchUsername) {
        this.searchUsername = searchUsername;
    }

    public String getSearchShortName() {
        return searchShortName;
    }

    public void setSearchShortName(String searchShortName) {
        this.searchShortName = searchShortName;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String[] getSearchUserRoleFlows() {
        return searchUserRoleFlows;
    }

    public void setSearchUserRoleFlows(String[] searchUserRoleFlows) {
        this.searchUserRoleFlows = searchUserRoleFlows;
    }
}
