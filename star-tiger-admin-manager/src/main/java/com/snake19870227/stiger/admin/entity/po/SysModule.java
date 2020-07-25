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
 * @since 2020-07-25
 */
@ApiModel(value="SysModule对象", description="")
public class SysModule implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "模块流水号")
    @TableId(value = "module_flow", type = IdType.ASSIGN_UUID)
    private String moduleFlow;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "排序码")
    private Integer moduleOrder;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getModuleFlow() {
        return moduleFlow;
    }

    public SysModule setModuleFlow(String moduleFlow) {
        this.moduleFlow = moduleFlow;
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public SysModule setModuleName(String moduleName) {
        this.moduleName = moduleName;
        return this;
    }

    public Integer getModuleOrder() {
        return moduleOrder;
    }

    public SysModule setModuleOrder(Integer moduleOrder) {
        this.moduleOrder = moduleOrder;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysModule setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysModule{" +
        "moduleFlow=" + moduleFlow +
        ", moduleName=" + moduleName +
        ", moduleOrder=" + moduleOrder +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
