package com.snake19870227.stiger.admin.sys.service;

import java.util.List;

import com.snake19870227.stiger.admin.common.TreeNode;
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.security.UserSecurityDetail;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/21
 */
public interface ISysExtService {

    UserInfo getUserInfo(String username);

    List<TreeNode<SysMenu>> treeMenu();

    List<TreeNode<SysMenu>> treeMenu(UserSecurityDetail userSecurityDetail);

    boolean saveRole(SysRole role, List<String> resourceFlows);

    List<String> getResourceFlowsByRole(String roleFlow);
}
