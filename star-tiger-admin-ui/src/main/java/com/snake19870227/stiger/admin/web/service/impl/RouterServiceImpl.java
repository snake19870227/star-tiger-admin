package com.snake19870227.stiger.admin.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.snake19870227.stiger.admin.dao.mapper.SysMenuMapper;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.web.service.RouterService;
import com.snake19870227.stiger.core.exception.ServiceException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/17
 */
@Service
public class RouterServiceImpl implements RouterService {

    private static final Logger logger = LoggerFactory.getLogger(RouterServiceImpl.class);

    private final SysMenuMapper sysMenuMapper;

    public RouterServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }

    @Override
    public SysMenu getRouterMenu(String menuCode) {
        SysMenu menu = sysMenuMapper.getMenuByMenuCode(menuCode);

        if (menu == null) {
            throw new ServiceException("未找到菜单信息");
        }

        return menu;
    }
}
