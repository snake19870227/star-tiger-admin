package com.snake19870227.stiger.admin.opt;

import java.util.List;

import com.snake19870227.stiger.admin.entity.bo.ResourceInfo;
import com.snake19870227.stiger.admin.entity.po.SysResource;

/**
 * @author Bu HuaYang
 */
public interface ResourceInfoOpt {

    List<SysResource> getByRoleCode(String roleCode);

    List<SysResource> getAll();

    ResourceInfo loadResourceInfo(String resourceFlow);
}
