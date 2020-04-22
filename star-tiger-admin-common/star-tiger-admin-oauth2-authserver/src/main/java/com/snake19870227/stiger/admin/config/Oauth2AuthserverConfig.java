package com.snake19870227.stiger.admin.config;

import java.security.KeyPair;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.access.AccessDeniedHandler;
import com.snake19870227.stiger.admin.oauth2.authserver.DefaultDbClientDetailsService;
import com.snake19870227.stiger.admin.oauth2.service.SysClientService;
import com.snake19870227.stiger.admin.security.AdminUser;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/22
 */
@Configuration
public class Oauth2AuthserverConfig {

    @Bean
    public ClientDetailsService clientDetailsService(SysClientService sysClientService) {
        return new DefaultDbClientDetailsService(sysClientService);
    }

    @Bean
    @Primary
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {
            @Override
            public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
                Map<String, Object> userInfoMap = new LinkedHashMap<>();
                userInfoMap.put(USERNAME, userAuthentication.getName());
                if (userAuthentication.getAuthorities() != null && !userAuthentication.getAuthorities().isEmpty()) {
                    userInfoMap.put(AUTHORITIES, AuthorityUtils.authorityListToSet(userAuthentication.getAuthorities()));
                }
                AdminUser adminUser = (AdminUser) userAuthentication.getPrincipal();
                userInfoMap.put("short_name", adminUser.getShortName());
                userInfoMap.put("user_flow", adminUser.getUserFlow());
                return userInfoMap;
            }
        });
        jwtAccessTokenConverter.setAccessTokenConverter(accessTokenConverter);
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("snake.keystore"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("com.snake19870227");
    }

    @Configuration
    @ConditionalOnProperty(prefix = "stiger.admin.oauth2", name = "enable", havingValue = "true")
    @EnableAuthorizationServer
    public static class ServerConfig extends AuthorizationServerConfigurerAdapter {

        private final ClientDetailsService clientDetailsService;
        private final TokenStore tokenStore;
        private final AuthenticationManager authenticationManager;
        private final JwtAccessTokenConverter jwtAccessTokenConverter;
        private final UserDetailsService userDetailsService;
        private final AccessDeniedHandler accessDeniedHandler;

        public ServerConfig(ClientDetailsService clientDetailsService,
                            TokenStore tokenStore,
                            AuthenticationManager authenticationManager,
                            JwtAccessTokenConverter jwtAccessTokenConverter,
                            UserDetailsService userDetailsService,
                            AccessDeniedHandler accessDeniedHandler) {
            this.clientDetailsService = clientDetailsService;
            this.tokenStore = tokenStore;
            this.authenticationManager = authenticationManager;
            this.jwtAccessTokenConverter = jwtAccessTokenConverter;
            this.userDetailsService = userDetailsService;
            this.accessDeniedHandler = accessDeniedHandler;
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.authenticationManager(authenticationManager)
                    .userDetailsService(userDetailsService)
                    .tokenStore(tokenStore)
                    .accessTokenConverter(jwtAccessTokenConverter)
            ;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.tokenKeyAccess("isAuthenticated()")
                    .checkTokenAccess("isAuthenticated()")
                    .allowFormAuthenticationForClients()
                    .accessDeniedHandler(accessDeniedHandler)
            ;
        }

    }


}
