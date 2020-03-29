package com.snake19870227.stiger.admin.service.sys;

import java.util.List;

import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.bo.ResourceInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.entity.po.SysResource;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public interface SysService {

    /* ====================< Resource >==================== */

    List<SysResource> getAllResource();

    ResourceInfo loadResourceInfo(String resourceFlow);

    List<SysResource> getResourceByRoleCode(String roleCode);

    RecordPage<SysResource> searchResources(String resName, long page, long pageSize);

    SysResource readResource(String resFlow);

    SysResource createResource(SysResource resource);

    SysResource modifyResource(SysResource resource);

    boolean deleteResource(String resFlow);

    /* ====================< Role >==================== */

    /* ====================< User >==================== */

    /* ====================< Menu >==================== */

    SysMenu getMenuByMenuCode(String menuCode);

    List<MenuInfo> allMenuTree();
}
