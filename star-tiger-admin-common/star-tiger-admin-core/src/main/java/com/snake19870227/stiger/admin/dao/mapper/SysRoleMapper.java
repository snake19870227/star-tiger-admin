package com.snake19870227.stiger.admin.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake19870227.stiger.admin.entity.po.SysRole;

/**
 * @author Bu HuaYang
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    default SysRole getByRoleCode(String roleCode) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code", roleCode);
        return this.selectOne(queryWrapper);
    }
}
