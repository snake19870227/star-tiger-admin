package com.snake19870227.stiger.admin.dao.mapper;

import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake19870227.stiger.admin.entity.po.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {

    default Optional<SysUser> queryByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return Optional.ofNullable(this.selectOne(wrapper));
    }

    default int changeLockState(String userFlow, String locked) {
        SysUser updater = new SysUser();
        updater.setUserFlow(userFlow).setLocked(locked);
        return this.updateById(updater);
    }
}
