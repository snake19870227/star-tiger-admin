package com.snake19870227.stiger.admin.oauth2;

import java.security.KeyPair;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import com.snake19870227.stiger.admin.security.AdminUser;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/18
 */
@Configuration
public class StarTigerAdminOauth2AutoConfiguration {

    @Bean
    public ClientDetailsService clientDetailsService(PasswordEncoder passwordEncoder) throws Exception {
        ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder> builder
                = new ClientDetailsServiceBuilder<>().inMemory();
        builder.withClient("client1")
                .secret(passwordEncoder.encode("123"))
                .authorizedGrantTypes("authorization_code", "password", "refresh_token")
                .scopes("user_base_info")
                .redirectUris(
                        "http://example.com"
                )
//                .accessTokenValiditySeconds(30)
//                .refreshTokenValiditySeconds(3600)
        ;
        return builder.build();
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
        KeyStoreKeyFactory keyStoreKeyFactory
                = new KeyStoreKeyFactory(
                new ClassPathResource("snake.keystore"),
                "123456".toCharArray()
        );
        return keyStoreKeyFactory.getKeyPair("com.snake19870227");
    }

}
