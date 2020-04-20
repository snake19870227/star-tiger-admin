package com.snake19870227.stiger.admin.service.sys.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.opt.sys.ClientInfoOpt;
import com.snake19870227.stiger.admin.service.sys.SysClientService;

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
}
