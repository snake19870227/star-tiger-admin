package com.snake19870227.stiger.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.security.CustomUserDetailsManager;
import com.snake19870227.stiger.admin.security.UiAuthenticationFailureHandler;
import com.snake19870227.stiger.admin.security.UiAuthenticationSuccessHandler;
import com.snake19870227.stiger.admin.security.WebSecurityExceptionHandler;
import com.snake19870227.stiger.admin.service.sys.SysMenuService;
import com.snake19870227.stiger.admin.service.sys.SysService;
import com.snake19870227.stiger.admin.service.sys.SysUserService;
import com.snake19870227.stiger.autoconfigure.properties.StarTigerFrameProperties;

/**
 * @author Bu HuaYang
 */
@Configuration
public class SecurityConfig {

    @Value("${stiger.admin.web.security.remember-me-key}")
    private String rememberMeKey;

    @Value("${stiger.admin.web.security.remember-me-cookie-name}")
    private String rememberMeCookieName;

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
        protected void configure(HttpSecurity http) throws Exception {

            String springActuatorPaths = springActuatorPath + "/**";

            http.csrf().ignoringAntMatchers(springActuatorPaths);
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

            http.exceptionHandling()
                    .authenticationEntryPoint(webSecurityExceptionHandler)
                    .accessDeniedHandler(webSecurityExceptionHandler)
            ;
        }
    }

    @Bean
    public UiAuthenticationSuccessHandler webAuthenticationSuccessHandler(SysService sysService, SysMenuService sysMenuService) {
        return new UiAuthenticationSuccessHandler(sysService, sysMenuService);
    }

    @Bean
    public UiAuthenticationFailureHandler webAuthenticationFailureHandler(StarTigerFrameProperties starTigerFrameProperties) {
        return new UiAuthenticationFailureHandler(starTigerFrameProperties);
    }

    @Bean
    public WebSecurityExceptionHandler webSecurityExceptionHandler(StarTigerFrameProperties starTigerFrameProperties, ObjectMapper objectMapper) {
        return new WebSecurityExceptionHandler(starTigerFrameProperties, objectMapper);
    }

    @Bean
    public UserDetailsManager userDetailsManager(SysUserService sysUserService) {
        return new CustomUserDetailsManager(sysUserService);
    }

    @Bean
    public RememberMeServices rememberMeServices(UserDetailsManager userDetailsManager) {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
//        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(rememberMeKey, userDetailsManager);
//        rememberMeServices.setParameter(Pr;
//        memberMeCookieName);
        return rememberMeServices;
    }
}
