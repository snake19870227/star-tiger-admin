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
@ApiModel(value="SysResource对象", description="")
public class SysResource implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "资源流水号")
    @TableId(value = "res_flow", type = IdType.ASSIGN_UUID)
    private String resFlow;

    @ApiModelProperty(value = "资源名称")
    private String resName;

    @ApiModelProperty(value = "资源路径")
    private String resPath;

    @ApiModelProperty(value = "操作范围")
    private String resMethod;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getResFlow() {
        return resFlow;
    }

    public SysResource setResFlow(String resFlow) {
        this.resFlow = resFlow;
        return this;
    }

    public String getResName() {
        return resName;
    }

    public SysResource setResName(String resName) {
        this.resName = resName;
        return this;
    }

    public String getResPath() {
        return resPath;
    }

    public SysResource setResPath(String resPath) {
        this.resPath = resPath;
        return this;
    }

    public String getResMethod() {
        return resMethod;
    }

    public SysResource setResMethod(String resMethod) {
        this.resMethod = resMethod;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysResource setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysResource{" +
        "resFlow=" + resFlow +
        ", resName=" + resName +
        ", resPath=" + resPath +
        ", resMethod=" + resMethod +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
