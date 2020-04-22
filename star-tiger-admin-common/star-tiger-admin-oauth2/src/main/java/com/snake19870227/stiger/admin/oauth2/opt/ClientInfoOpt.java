package com.snake19870227.stiger.admin.oauth2.opt;

import java.util.List;

import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.po.SysExtClient;
import com.snake19870227.stiger.admin.entity.po.SysExtClientScope;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/19
 */
public interface ClientInfoOpt {

    ClientInfo getClientInfoById(String clientId);

    RecordPage<SysExtClient> getClientInfo(long page, long pageSize);

    SysExtClient getClientById(String clientId);

    List<SysExtClientScope> getClientScopes(String clientFlow);
}
