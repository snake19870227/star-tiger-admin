package com.snake19870227.stiger.admin.oauth2;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.entity.po.SysExtClient;
import com.snake19870227.stiger.admin.service.sys.SysClientService;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/20
 */
public class StarTigerClientDetailsService implements ClientDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(StarTigerClientDetailsService.class);

    private final SysClientService sysClientService;

    public StarTigerClientDetailsService(SysClientService sysClientService) {
        this.sysClientService = sysClientService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        ClientInfo clientInfo = sysClientService.getClientInfoById(clientId);

        SysExtClient client = clientInfo.getClient();

        BaseClientDetails clientDetails = new BaseClientDetails();

        clientDetails.setClientId(client.getClientId());
        clientDetails.setClientSecret(client.getClientSecret());

        if (StrUtil.isNotBlank(client.getGrantTypes())) {
            clientDetails.setAuthorizedGrantTypes(Arrays.asList(StrUtil.split(client.getGrantTypes(), ",")));
        }

        if (StrUtil.isNotBlank(client.getRedirectUrl())) {
            clientDetails.setRegisteredRedirectUri(new HashSet<>(Collections.singleton(client.getRedirectUrl())));
        }

        if (client.getAccessTokenValiditySeconds() != null && client.getAccessTokenValiditySeconds() > 0) {
            clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
        }

        if (client.getRefreshTokenValiditySeconds() != null && client.getRefreshTokenValiditySeconds() > 0) {
            clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
        }

        return clientDetails;
    }
}
