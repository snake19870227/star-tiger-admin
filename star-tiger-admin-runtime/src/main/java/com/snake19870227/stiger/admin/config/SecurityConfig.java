package com.snake19870227.stiger.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.StarTigerAdminConstant.UrlPath;
import com.snake19870227.stiger.admin.oauth2.StarTigerAdminOauth2AutoConfiguration;
import com.snake19870227.stiger.admin.security.WebSecurityExceptionHandler;
import com.snake19870227.stiger.admin.web.security.UiAuthenticationFailureHandler;
import com.snake19870227.stiger.admin.web.security.UiAuthenticationSuccessHandler;

/**
 * @author Bu HuaYang
 */
@Configuration
public class SecurityConfig {

    @Configuration
    public static class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Value("${management.endpoints.web.base-path:/actuator}")
        private String springActuatorPath;

        @Value("${stiger.admin.web.security.remember-me-key}")
        private String rememberMeKey;

        private final UiAuthenticationSuccessHandler uiAuthenticationSuccessHandler;
        private final UiAuthenticationFailureHandler uiAuthenticationFailureHandler;

        private final WebSecurityExceptionHandler webSecurityExceptionHandler;

        private final RememberMeServices rememberMeServices;

        public CustomWebSecurityConfigurerAdapter(UiAuthenticationSuccessHandler uiAuthenticationSuccessHandler,
                                                  UiAuthenticationFailureHandler uiAuthenticationFailureHandler,
                                                  WebSecurityExceptionHandler webSecurityExceptionHandler,
                                                  RememberMeServices rememberMeServices) {
            this.uiAuthenticationSuccessHandler = uiAuthenticationSuccessHandler;
            this.uiAuthenticationFailureHandler = uiAuthenticationFailureHandler;
            this.webSecurityExceptionHandler = webSecurityExceptionHandler;
            this.rememberMeServices = rememberMeServices;
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            String springActuatorPaths = springActuatorPath + "/**";

            http.csrf().ignoringAntMatchers(springActuatorPaths, UrlPath.OAUTH_PATTERN);
            http.headers().frameOptions().sameOrigin();

            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();

            urlRegistry
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers(StarTigerAdminConstant.UrlPath.anonymousPaths()).permitAll()
                    .antMatchers(StarTigerAdminConstant.UrlPath.authenticatedPaths()).authenticated()
                    .anyRequest().access("@authAssert.canAccess(request, authentication)");

            http.formLogin()
                    .failureHandler(uiAuthenticationFailureHandler)
                    .successHandler(uiAuthenticationSuccessHandler)
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(StarTigerAdminConstant.UrlPath.LOGOUT, "GET"))
                .and()
                .rememberMe()
                    .key(rememberMeKey)
                    .rememberMeServices(rememberMeServices)
                    .authenticationSuccessHandler(uiAuthenticationSuccessHandler)
                .and()
                .httpBasic()
                .and().sessionManagement().maximumSessions(1);

            http.oauth2ResourceServer().jwt()
            ;

            http.exceptionHandling()
                    .authenticationEntryPoint(webSecurityExceptionHandler)
                    .accessDeniedHandler(webSecurityExceptionHandler)
            ;
        }
    }

    @Configuration
    @ConditionalOnBean(StarTigerAdminOauth2AutoConfiguration.class)
    @ConditionalOnProperty(prefix = "stiger.admin.oauth2", name = "enable", havingValue = "true")
    @EnableAuthorizationServer
    public static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

        private final ClientDetailsService clientDetailsService;
        private final TokenStore tokenStore;
        private final AuthenticationManager authenticationManager;
        private final JwtAccessTokenConverter jwtAccessTokenConverter;
        private final UserDetailsService userDetailsService;

        public AuthorizationServerConfig(ClientDetailsService clientDetailsService,
                                         TokenStore tokenStore,
                                         AuthenticationManager authenticationManager,
                                         JwtAccessTokenConverter jwtAccessTokenConverter,
                                         UserDetailsService userDetailsService) {
            this.clientDetailsService = clientDetailsService;
            this.tokenStore = tokenStore;
            this.authenticationManager = authenticationManager;
            this.jwtAccessTokenConverter = jwtAccessTokenConverter;
            this.userDetailsService = userDetailsService;
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
            ;
        }
    }
}
