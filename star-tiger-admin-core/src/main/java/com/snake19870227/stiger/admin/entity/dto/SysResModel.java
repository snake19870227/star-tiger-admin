package com.snake19870227.stiger.admin.entity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.snake19870227.stiger.admin.enums.ResourceMethod;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/24
 */
public class SysResModel {

    private String resFlow;

    @NotNull
    @Size(max = 20)
    private String resName;

    @NotNull
    @Size(max = 400)
    private String resPath;

    @NotNull
    private ResourceMethod resMethod;

    public String getResFlow() {
        return resFlow;
    }

    public void setResFlow(String resFlow) {
        this.resFlow = resFlow;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }

    public ResourceMethod getResMethod() {
        return resMethod;
    }

    public void setResMethod(ResourceMethod resMethod) {
        this.resMethod = resMethod;
    }
}
