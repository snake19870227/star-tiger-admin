package com.snake19870227.stiger.admin.opt.sys;

import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.entity.po.SysExtClient;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/19
 */
public interface ClientInfoOpt {

    ClientInfo getClientInfoById(String clientId);

    SysExtClient getClientById(String clientId);
}
