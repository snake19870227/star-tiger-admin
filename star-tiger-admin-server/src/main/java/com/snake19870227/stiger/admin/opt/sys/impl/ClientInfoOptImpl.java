package com.snake19870227.stiger.admin.opt.sys.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.snake19870227.stiger.admin.dao.mapper.SysExtClientMapper;
import com.snake19870227.stiger.admin.dao.mapper.SysExtClientScopeMapper;
import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.entity.po.SysExtClient;
import com.snake19870227.stiger.admin.entity.po.SysExtClientScope;
import com.snake19870227.stiger.admin.opt.sys.ClientInfoOpt;
import com.snake19870227.stiger.core.exception.OptException;
import com.snake19870227.stiger.core.utils.BusinessAssert;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/19
 */
@Component
public class ClientInfoOptImpl implements ClientInfoOpt {

    private static final Logger logger = LoggerFactory.getLogger(ClientInfoOptImpl.class);

    private final SysExtClientMapper clientMapper;

    private final SysExtClientScopeMapper clientScopeMapper;

    public ClientInfoOptImpl(SysExtClientMapper clientMapper, SysExtClientScopeMapper clientScopeMapper) {
        this.clientMapper = clientMapper;
        this.clientScopeMapper = clientScopeMapper;
    }

    @Override
    public ClientInfo getClientInfoById(String clientId) {
        SysExtClient client = getClient(clientId);

        BusinessAssert.notNull(client, "未找到客户端", OptException.class);

        List<SysExtClientScope> scopes = getClientScope(client.getClientFlow());

        BusinessAssert.notEmpty(scopes, "客户端未获得任何授权", OptException.class);

        return ClientInfo.of(client, scopes);
    }

    @Override
    public SysExtClient getClientById(String clientId) {
        return getClient(clientId);
    }

    private SysExtClient getClient(String clientId) {
        QueryWrapper<SysExtClient> wrapper = new QueryWrapper<>();
        wrapper.eq("client_id", clientId);
        return clientMapper.selectOne(wrapper);
    }

    private List<SysExtClientScope> getClientScope(String clientFlow) {
        QueryWrapper<SysExtClientScope> wrapper = new QueryWrapper<>();
        wrapper.eq("client_flow", clientFlow);
        return clientScopeMapper.selectList(wrapper);
    }
}
