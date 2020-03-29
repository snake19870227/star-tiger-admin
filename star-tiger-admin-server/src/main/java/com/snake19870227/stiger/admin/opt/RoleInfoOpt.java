package com.snake19870227.stiger.admin.opt;

import java.util.List;

import com.snake19870227.stiger.admin.entity.bo.RoleInfo;
import com.snake19870227.stiger.admin.entity.po.SysRole;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/20
 */
public interface RoleInfoOpt {

    List<SysRole> getAll();

    RoleInfo readRoleInfo(String roleFlow);
}
