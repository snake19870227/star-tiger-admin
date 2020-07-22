package com.snake19870227.stiger.admin.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/21
 */
public interface SysExtMapper {

    List<SysRole> selectRoleByUser(@Param("userFlow") String userFlow);

    List<SysResource> selectResourceByUser(@Param("userFlow") String userFlow);
}
