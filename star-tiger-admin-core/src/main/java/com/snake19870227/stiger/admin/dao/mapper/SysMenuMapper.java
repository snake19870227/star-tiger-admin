package com.snake19870227.stiger.admin.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snake19870227.stiger.admin.entity.po.SysMenu;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    default SysMenu getMenuByMenuCode(String menuCode) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_code", menuCode);
        return this.selectOne(queryWrapper);
    }

    default List<SysMenu> getChildMenu(String parentMenuFlow) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_menu_flow", parentMenuFlow);
        return this.selectList(queryWrapper);
    }

    default List<SysMenu> getAllMenu(int level) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_level", level);
        return this.selectList(queryWrapper);
    }
}
