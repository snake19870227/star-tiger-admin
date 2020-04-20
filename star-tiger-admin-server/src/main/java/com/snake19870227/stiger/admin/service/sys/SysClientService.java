package com.snake19870227.stiger.admin.service.sys;

import com.snake19870227.stiger.admin.entity.bo.ClientInfo;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/20
 */
public interface SysClientService {

    ClientInfo getClientInfoById(String clientId);
}
