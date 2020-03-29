package com.snake19870227.stiger.admin.service.sys;

import java.util.List;
import java.util.Optional;

import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.bo.RoleInfo;
import com.snake19870227.stiger.admin.entity.po.SysRole;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/24
 */
public interface SysRoleService {

    Optional<SysRole> readRoleByRoleCode(String roleCode);

    List<SysRole> getAllRoles();

    RecordPage<SysRole> searchRoles(String searchCode, String searchName, String searchResName, long page, long pageSize);

    RoleInfo readRoleInfo(String roleFlow);

    boolean deleteRole(String roleFlow);

    SysRole createRole(SysRole role, String[] resFlows);

    SysRole modifyRole(SysRole role, String[] resFlows);
}
