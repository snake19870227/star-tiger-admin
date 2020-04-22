package com.snake19870227.stiger.admin.oauth2.service;

import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/20
 */
public interface SysClientService {

    ClientInfo getClientInfoById(String clientId);

    RecordPage<ClientInfo> getClientInfo(long page, long pageSize);
}
