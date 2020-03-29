package com.snake19870227.stiger.admin.opt.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.snake19870227.stiger.admin.dao.mapper.SysMenuMapper;
import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.opt.MenuInfoOpt;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
@Component
public class MenuInfoOptImpl implements MenuInfoOpt {

    private static final Logger logger = LoggerFactory.getLogger(MenuInfoOptImpl.class);

    private final SysMenuMapper sysMenuMapper;

    public MenuInfoOptImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    @Cacheable(cacheNames = "SysMenu", key = "'level1'")
    public List<SysMenu> getAllLevel1Menu() {
        return sysMenuMapper.getAllMenu(1);
    }

    @Override
    @Cacheable(cacheNames = "MenuInfo", key = "#parentMenuFlow")
    public MenuInfo menuTree(String parentMenuFlow) {
        SysMenu menu = sysMenuMapper.selectById(parentMenuFlow);
        List<SysMenu> childMenus = sysMenuMapper.getChildMenu(parentMenuFlow);
        return new MenuInfo(menu, childMenus);
    }

    @Override
    @Cacheable(cacheNames = "MenuInfo", key = "#parentMenu.menuFlow")
    public MenuInfo menuTree(SysMenu parentMenu) {
        List<SysMenu> childMenus = sysMenuMapper.getChildMenu(parentMenu.getMenuFlow());
        return new MenuInfo(parentMenu, childMenus);
    }
}
