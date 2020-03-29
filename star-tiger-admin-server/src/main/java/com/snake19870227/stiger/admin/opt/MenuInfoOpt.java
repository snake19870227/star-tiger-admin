package com.snake19870227.stiger.admin.opt;

import java.util.List;

import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public interface MenuInfoOpt {

    List<SysMenu> getAllLevel1Menu();

    MenuInfo menuTree(String parentMenuFlow);

    MenuInfo menuTree(SysMenu parentMenu);
}
