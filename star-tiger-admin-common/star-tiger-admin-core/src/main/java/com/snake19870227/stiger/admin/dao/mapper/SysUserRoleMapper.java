package com.snake19870227.stiger.admin.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake19870227.stiger.admin.entity.po.SysUserRole;

/**
 * @author Bu HuaYang
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    default List<SysUserRole> queryByUserFlow(String userFlow) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_flow", userFlow);
        return this.selectList(wrapper);
    }

    default int deleteByUserFlow(String userFlow) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_flow", userFlow);
        return this.delete(wrapper);
    }
}
