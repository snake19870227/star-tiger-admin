package com.snake19870227.stiger.admin.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snake19870227.stiger.admin.common.TreeNode;
import com.snake19870227.stiger.admin.dao.base.SysMenuMapper;
import com.snake19870227.stiger.admin.dao.base.SysUserMapper;
import com.snake19870227.stiger.admin.dao.ext.SysExtMapper;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.admin.security.UserSecurityDetail;
import com.snake19870227.stiger.admin.sys.service.ISysExtService;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/21
 */
@Service
public class SysExtServiceImpl implements ISysExtService {

    private static final Logger logger = LoggerFactory.getLogger(SysExtServiceImpl.class);

    private final SysUserMapper sysUserMapper;

    private final SysMenuMapper sysMenuMapper;

    private final SysExtMapper sysExtMapper;

    public SysExtServiceImpl(SysUserMapper sysUserMapper,
                             SysMenuMapper sysMenuMapper,
                             SysExtMapper sysExtMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysMenuMapper = sysMenuMapper;
        this.sysExtMapper = sysExtMapper;
    }

    @Override
    public UserInfo getUserInfo(String username) {

        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);

        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);

        if (sysUser == null) {
            return null;
        }

        List<SysRole> sysRoles = sysExtMapper.selectRoleByUser(sysUser.getUserFlow());

        List<SysResource> sysResources = sysExtMapper.selectResourceByUser(sysUser.getUserFlow());

        return new UserInfo(sysUser, sysRoles, sysResources);
    }

    @Override
    public List<TreeNode<SysMenu>> treeMenu() {

        List<SysMenu> menus = allMenus();

        return buildTreeNode(menus);
    }

    @Override
    public List<TreeNode<SysMenu>> treeMenu(UserSecurityDetail userSecurityDetail) {

        List<SysMenu> menus = allMenus();

        AntPathMatcher pathMatcher = new AntPathMatcher();

        List<SysMenu> userMenus = menus.stream().filter(menu -> {
            for (AntPathRequestMatcher requestMatcher : userSecurityDetail.getMatchers()) {
                boolean flag = pathMatcher.match(requestMatcher.getPattern(), menu.getMenuPath());
                if (StrUtil.isBlank(menu.getMenuPath()) || flag) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());

        return buildTreeNode(userMenus);
    }

    private List<SysMenu> allMenus() {
        QueryWrapper<SysMenu> menuQueryWrapper = new QueryWrapper<>();

        menuQueryWrapper.orderByAsc("menu_order");

        return sysMenuMapper.selectList(menuQueryWrapper);
    }

    private TreeNode<SysMenu> createMenuTreeNode(SysMenu menu) {
        return TreeNode.create(menu, (node, data) -> {
            node.setId(data.getMenuFlow());
            node.setTitle(data.getMenuName());
        });
    }

    private List<TreeNode<SysMenu>> buildTreeNode(List<SysMenu> menus) {
        List<TreeNode<SysMenu>> menuTree = new ArrayList<>();

        Map<String, TreeNode<SysMenu>> levelOneMenus = new LinkedHashMap<>();

        menus.stream().filter(menu -> {
            if (StrUtil.isBlank(menu.getParentMenuFlow())
                    && menu.getMenuLevel() == 1) {
                TreeNode<SysMenu> treeNode = createMenuTreeNode(menu);
                menuTree.add(treeNode);
                levelOneMenus.put(menu.getMenuFlow(), treeNode);
                return false;
            }
            return true;
        }).forEach(menu -> {
            if (levelOneMenus.containsKey(menu.getParentMenuFlow())) {
                TreeNode<SysMenu> parentTreeNode = levelOneMenus.get(menu.getParentMenuFlow());
                TreeNode<SysMenu> treeNode = createMenuTreeNode(menu);
                treeNode.setParentNode(parentTreeNode);
                parentTreeNode.getChildren().add(treeNode);
            }
        });

        return menuTree.stream()
                .filter(treeNode -> CollUtil.isNotEmpty(treeNode.getChildren()))
                .collect(Collectors.toList());
    }
}
