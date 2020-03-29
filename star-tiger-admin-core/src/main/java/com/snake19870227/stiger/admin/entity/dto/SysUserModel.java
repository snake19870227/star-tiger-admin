package com.snake19870227.stiger.admin.entity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/26
 */
public class SysUserModel {

    private String userFlow;

    @NotNull
    @Size(min = 6, max = 12)
    private String username;

    private String encodePassword;

    @NotNull
    @Size(min = 2, max = 20)
    private String shortName;

    private String locked;

    public String getUserFlow() {
        return userFlow;
    }

    public void setUserFlow(String userFlow) {
        this.userFlow = userFlow;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncodePassword() {
        return encodePassword;
    }

    public void setEncodePassword(String encodePassword) {
        this.encodePassword = encodePassword;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }
}
