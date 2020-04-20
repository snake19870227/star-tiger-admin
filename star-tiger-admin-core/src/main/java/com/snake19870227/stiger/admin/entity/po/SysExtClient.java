package com.snake19870227.stiger.admin.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

/**
 * <p>
 * 
 * </p>
 *
 * @author buhuayang
 * @since 2020-04-19
 */
@ApiModel(value="SysExtClient对象", description="")
public class SysExtClient implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "客户端流水号")
    @TableId(value = "client_flow", type = IdType.ASSIGN_UUID)
    private String clientFlow;

    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "客户端Secret")
    private String clientSecret;

    @ApiModelProperty(value = "模式")
    private String grantTypes;

    @ApiModelProperty(value = "获取code重定向地址")
    private String redirectUrl;

    @ApiModelProperty(value = "令牌有效期(秒)")
    private Integer accessTokenValiditySeconds;

    @ApiModelProperty(value = "刷新令牌有效期(秒)")
    private Integer refreshTokenValiditySeconds;

    @ApiModelProperty(value = "指定的资源服务器ID")
    private String resourceIds;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getClientFlow() {
        return clientFlow;
    }

    public SysExtClient setClientFlow(String clientFlow) {
        this.clientFlow = clientFlow;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public SysExtClient setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public SysExtClient setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getGrantTypes() {
        return grantTypes;
    }

    public SysExtClient setGrantTypes(String grantTypes) {
        this.grantTypes = grantTypes;
        return this;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public SysExtClient setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public SysExtClient setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        return this;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public SysExtClient setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        return this;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public SysExtClient setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysExtClient setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysExtClient{" +
        "clientFlow=" + clientFlow +
        ", clientId=" + clientId +
        ", clientSecret=" + clientSecret +
        ", grantTypes=" + grantTypes +
        ", redirectUrl=" + redirectUrl +
        ", accessTokenValiditySeconds=" + accessTokenValiditySeconds +
        ", refreshTokenValiditySeconds=" + refreshTokenValiditySeconds +
        ", resourceIds=" + resourceIds +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
