package com.snake19870227.stiger.admin.oauth2.service.impl;

import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.po.SysExtClient;
import com.snake19870227.stiger.admin.entity.po.SysExtClientScope;
import com.snake19870227.stiger.admin.oauth2.opt.ClientInfoOpt;
import com.snake19870227.stiger.admin.oauth2.service.SysClientService;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/20
 */
@Service
public class SysClientServiceImpl implements SysClientService {

    private static final Logger logger = LoggerFactory.getLogger(SysClientServiceImpl.class);

    private final ClientInfoOpt clientInfoOpt;

    public SysClientServiceImpl(ClientInfoOpt clientInfoOpt) {
        this.clientInfoOpt = clientInfoOpt;
    }

    @Override
    public ClientInfo getClientInfoById(String clientId) {
        return clientInfoOpt.getClientInfoById(clientId);
    }

    @Override
    public RecordPage<ClientInfo> getClientInfo(long page, long pageSize) {
        List<ClientInfo> records = new ArrayList<>();
        RecordPage<SysExtClient> clients = clientInfoOpt.getClientInfo(page, pageSize);
        RecordPage<ClientInfo> clientInfos = new RecordPage<>(clients.getCurrent(), clients.getSize());
        clientInfos.setTotal(clients.getTotal());
        if (CollUtil.isNotEmpty(clients.getRecords())) {
            clients.getRecords().forEach(client -> {
                List<SysExtClientScope> scopes = clientInfoOpt.getClientScopes(client.getClientFlow());
                records.add(ClientInfo.of(client, scopes));
            });
        }
        clientInfos.setRecords(records);
        return clientInfos;
    }
}
