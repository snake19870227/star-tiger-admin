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

    @Override
    public String toString() {
        return "SysUserSubject{" +
        "subjectFlow=" + subjectFlow +
        ", orgFlow=" + orgFlow +
        ", deptFlow=" + deptFlow +
        ", userFlow=" + userFlow +
        "}";
    }
}
