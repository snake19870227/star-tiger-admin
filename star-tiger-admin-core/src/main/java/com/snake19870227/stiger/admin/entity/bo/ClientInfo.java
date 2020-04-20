package com.snake19870227.stiger.admin.entity.bo;

import java.util.List;

import com.snake19870227.stiger.admin.entity.po.SysExtClient;
import com.snake19870227.stiger.admin.entity.po.SysExtClientScope;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/19
 */
public class ClientInfo {

    private SysExtClient client;

    private List<SysExtClientScope> scopes;

    public static ClientInfo of(SysExtClient client, List<SysExtClientScope> scopes) {
        ClientInfo info = new ClientInfo();
        info.setClient(client);
        info.setScopes(scopes);
        return info;
    }

    public SysExtClient getClient() {
        return client;
    }

    public void setClient(SysExtClient client) {
        this.client = client;
    }

    public List<SysExtClientScope> getScopes() {
        return scopes;
    }

    public void setScopes(List<SysExtClientScope> scopes) {
        this.scopes = scopes;
    }
}
