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
 * @since 2020-07-21
 */
@ApiModel(value="SysUser对象", description="")
public class SysUser implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户流水号")
    @TableId(value = "user_flow", type = IdType.ASSIGN_UUID)
    private String userFlow;

    @ApiModelProperty(value = "用户登录名")
    private String username;

    @ApiModelProperty(value = "用户登录密码")
    private String encodePassword;

    @ApiModelProperty(value = "短名称(用于显示)")
    private String shortName;

    @ApiModelProperty(value = "是否锁定")
    private String locked;

    @ApiModelProperty(value = "到期时间")
    private String expireDateTime;

    @ApiModelProperty(value = "是否过期")
    private String expired;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getUserFlow() {
        return userFlow;
    }

    public SysUser setUserFlow(String userFlow) {
        this.userFlow = userFlow;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SysUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEncodePassword() {
        return encodePassword;
    }

    public SysUser setEncodePassword(String encodePassword) {
        this.encodePassword = encodePassword;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public SysUser setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getLocked() {
        return locked;
    }

    public SysUser setLocked(String locked) {
        this.locked = locked;
        return this;
    }

    public String getExpireDateTime() {
        return expireDateTime;
    }

    public SysUser setExpireDateTime(String expireDateTime) {
        this.expireDateTime = expireDateTime;
        return this;
    }

    public String getExpired() {
        return expired;
    }

    public SysUser setExpired(String expired) {
        this.expired = expired;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysUser setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        "userFlow=" + userFlow +
        ", username=" + username +
        ", encodePassword=" + encodePassword +
        ", shortName=" + shortName +
        ", locked=" + locked +
        ", expireDateTime=" + expireDateTime +
        ", expired=" + expired +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
