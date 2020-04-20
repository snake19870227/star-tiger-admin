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
 * @since 2020-04-19
 */
@ApiModel(value="SysExtClientScope对象", description="")
public class SysExtClientScope implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "客户端权限范围信息流水号")
    @TableId(value = "client_scope_flow", type = IdType.ASSIGN_UUID)
    private String clientScopeFlow;

    @ApiModelProperty(value = "客户端流水号")
    private String clientFlow;

    @ApiModelProperty(value = "权限范围")
    private String scope;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getClientScopeFlow() {
        return clientScopeFlow;
    }

    public SysExtClientScope setClientScopeFlow(String clientScopeFlow) {
        this.clientScopeFlow = clientScopeFlow;
        return this;
    }

    public String getClientFlow() {
        return clientFlow;
    }

    public SysExtClientScope setClientFlow(String clientFlow) {
        this.clientFlow = clientFlow;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public SysExtClientScope setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysExtClientScope setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysExtClientScope{" +
        "clientScopeFlow=" + clientScopeFlow +
        ", clientFlow=" + clientFlow +
        ", scope=" + scope +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
