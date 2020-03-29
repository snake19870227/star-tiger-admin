package com.snake19870227.stiger.admin.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author buhuayang
 * @since 2020-03-27
 */
@ApiModel(value="SysDept对象", description="")
public class SysDept implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "部门流水号")
    @TableId(value = "dept_flow", type = IdType.ASSIGN_UUID)
    private String deptFlow;

    @ApiModelProperty(value = "机构流水号")
    private String orgFlow;

    @ApiModelProperty(value = "部门代码")
    private String deptCode;

    @ApiModelProperty(value = "部门名称")
    private String deptName;


    public String getDeptFlow() {
        return deptFlow;
    }

    public SysDept setDeptFlow(String deptFlow) {
        this.deptFlow = deptFlow;
        return this;
    }

    public String getOrgFlow() {
        return orgFlow;
    }

    public SysDept setOrgFlow(String orgFlow) {
        this.orgFlow = orgFlow;
        return this;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public SysDept setDeptCode(String deptCode) {
        this.deptCode = deptCode;
        return this;
    }

    public String getDeptName() {
        return deptName;
    }

    public SysDept setDeptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    @Override
    public String toString() {
        return "SysDept{" +
        "deptFlow=" + deptFlow +
        ", orgFlow=" + orgFlow +
        ", deptCode=" + deptCode +
        ", deptName=" + deptName +
        "}";
    }
}
