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
@ApiModel(value="SysUserSubject对象", description="")
public class SysUserSubject implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户隶属信息流水号")
    @TableId(value = "subject_flow", type = IdType.ASSIGN_UUID)
    private String subjectFlow;

    @ApiModelProperty(value = "机构流水号")
    private String orgFlow;

    @ApiModelProperty(value = "部门流水号")
    private String deptFlow;

    @ApiModelProperty(value = "用户流水号")
    private String userFlow;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private String deleteFlag;


    public String getSubjectFlow() {
        return subjectFlow;
    }

    public SysUserSubject setSubjectFlow(String subjectFlow) {
        this.subjectFlow = subjectFlow;
        return this;
    }

    public String getOrgFlow() {
        return orgFlow;
    }

    public SysUserSubject setOrgFlow(String orgFlow) {
        this.orgFlow = orgFlow;
        return this;
    }

    public String getDeptFlow() {
        return deptFlow;
    }

    public SysUserSubject setDeptFlow(String deptFlow) {
        this.deptFlow = deptFlow;
        return this;
    }

    public String getUserFlow() {
        return userFlow;
    }

    public SysUserSubject setUserFlow(String userFlow) {
        this.userFlow = userFlow;
        return this;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public SysUserSubject setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    @Override
    public String toString() {
        return "SysUserSubject{" +
        "subjectFlow=" + subjectFlow +
        ", orgFlow=" + orgFlow +
        ", deptFlow=" + deptFlow +
        ", userFlow=" + userFlow +
        ", deleteFlag=" + deleteFlag +
        "}";
    }
}
