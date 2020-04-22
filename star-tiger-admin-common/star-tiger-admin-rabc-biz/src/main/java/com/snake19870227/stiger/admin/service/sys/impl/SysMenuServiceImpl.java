package com.snake19870227.stiger.admin.service.sys.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.snake19870227.stiger.admin.dao.mapper.SysMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysMenuMapper;
import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.opt.sys.MenuInfoOpt;
import com.snake19870227.stiger.admin.service.sys.SysMenuService;
import com.snake19870227.stiger.core.exception.ServiceException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/02
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    private static final Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    private final SysMenuMapper sysMenuMapper;

    private final SysMapper sysMapper;

    private final MenuInfoOpt menuInfoOpt;

    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper, SysMapper sysMapper, MenuInfoOpt menuInfoOpt) {
        this.sysMenuMapper = sysMenuMapper;
        this.sysMapper = sysMapper;
        this.menuInfoOpt = menuInfoOpt;
    }

    @Override
    public SysMenu getByMenuFlow(String menuFlow) {
        return sysMenuMapper.selectById(menuFlow);
    }

    @Override
    public SysMenu getMenuByMenuCode(String menuCode) {
        return sysMenuMapper.getMenuByMenuCode(menuCode);
    }

    @Override
    public List<MenuInfo> allMenuTree() {
        List<SysMenu> level1Menus = menuInfoOpt.getAllLevel1Menu();
        return level1Menus.stream().map(menuInfoOpt::menuTree).collect(Collectors.toList());
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "SysMenu", key = "#menu.menuFlow")
            },
            evict = {
                    @CacheEvict(cacheNames = "MenuInfo", key = "#menu.parentMenuFlow",
                            condition = "T(cn.hutool.core.util.StrUtil).isNotBlank(#menu.parentMenuFlow) and #menu.menuLevel == 2"),
                    @CacheEvict(cacheNames = "SysMenu", key = "'level1'",
                            condition = "T(cn.hutool.core.util.StrUtil).isBlank(#menu.parentMenuFlow) and #menu.menuLevel == 1")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysMenu create(SysMenu menu) {
        int maxOrder = sysMapper.selectMaxMenuOrder();
        menu.setMenuOrder(maxOrder + 1);
        if (sysMenuMapper.insert(menu) == 1) {
            return menu;
        }
        return null;
    }

    @Override
    @Caching(
            put = {
                    @CachePut(cacheNames = "SysMenu", key = "#menu.menuFlow")
            },
            evict = {
                    @CacheEvict(cacheNames = "MenuInfo", key = "#menu.parentMenuFlow",
                            condition = "T(cn.hutool.core.util.StrUtil).isNotBlank(#menu.parentMenuFlow) and #menu.menuLevel == 2"),
                    @CacheEvict(cacheNames = "SysMenu", key = "'level1'",
                            condition = "T(cn.hutool.core.util.StrUtil).isBlank(#menu.parentMenuFlow) and #menu.menuLevel == 1")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysMenu modify(SysMenu menu) {
        if (sysMenuMapper.updateById(menu) == 1) {
            return menu;
        }
        return null;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "MenuInfo", key = "#result.parentMenuFlow",
                            condition = "T(cn.hutool.core.util.StrUtil).isNotBlank(#result.parentMenuFlow) and #result.menuLevel == 2"),
                    @CacheEvict(cacheNames = "SysMenu", key = "'level1'",
                            condition = "T(cn.hutool.core.util.StrUtil).isBlank(#result.parentMenuFlow) and #result.menuLevel == 1")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysMenu delete(String menuFlow) {
        SysMenu menu = sysMenuMapper.selectById(menuFlow);
        if (menu == null) {
            throw new ServiceException("未找到菜单");
        }
        if (sysMenuMapper.deleteById(menuFlow) == 1) {
            return menu;
        }
        return null;
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "MenuInfo", key = "#result.parentMenuFlow",
                            condition = "T(cn.hutool.core.util.StrUtil).isNotBlank(#result.parentMenuFlow) and #result.menuLevel == 2"),
                    @CacheEvict(cacheNames = "SysMenu", key = "'level1'",
                            condition = "T(cn.hutool.core.util.StrUtil).isBlank(#result.parentMenuFlow) and #result.menuLevel == 1")
            }
    )
    @Transactional(rollbackFor = Exception.class)
    public SysMenu swapOrder(String menuFlow1, String menuFlow2) {
        SysMenu menu1 = sysMenuMapper.selectById(menuFlow1);
        SysMenu menu2 = sysMenuMapper.selectById(menuFlow2);
        if (menu1 == null || menu2 == null) {
            throw new ServiceException("未找到菜单");
        }
        int order1 = menu1.getMenuOrder();
        int order2 = menu2.getMenuOrder();
        menu1.setMenuOrder(order2);
        menu2.setMenuOrder(order1);
        int n1 = sysMenuMapper.updateById(menu1);
        int n2 = sysMenuMapper.updateById(menu2);
        if (n1 == 1 && n2 == 1) {
            return menu1;
        }
        return null;
    }
}
