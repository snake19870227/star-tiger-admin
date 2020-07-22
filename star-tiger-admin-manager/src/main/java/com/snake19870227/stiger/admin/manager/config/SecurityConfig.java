package com.snake19870227.stiger.admin.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import com.snake19870227.stiger.admin.common.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.manager.security.ManagerAuthSuccessHandler;

/**
 * @author Bu HuaYang
 */
@Configuration
public class SecurityConfig {

    @Configuration
    public static class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Value("${management.endpoints.web.base-path:/actuator}")
        private String springActuatorPath;

//        @Value("${stiger.admin.web.security.remember-me-key}")
//        private String rememberMeKey;

//        @Value("${stiger.admin.oauth2.enable:false}")
//        private boolean enableOauth2;

        private final ManagerAuthSuccessHandler managerAuthSuccessHandler;

        public CustomWebSecurityConfigurerAdapter(ManagerAuthSuccessHandler managerAuthSuccessHandler) {
            this.managerAuthSuccessHandler = managerAuthSuccessHandler;
        }
//        private final WebAuthenticationFailureHandler webAuthenticationFailureHandler;

//        private final WebSecurityExceptionHandler webSecurityExceptionHandler;

//        private final RememberMeServices rememberMeServices;

//        @Override
//        @Bean
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();

            urlRegistry
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers(StarTigerAdminConstant.UrlPath.anonymousPaths()).permitAll()
//                    .antMatchers(StarTigerAdminConstant.UrlPath.authenticatedPaths()).authenticated()
//                    .anyRequest().access("@authAssert.canAccess(request, authentication)")
                    .anyRequest().authenticated()
            ;

            http.csrf()
//                    .ignoringAntMatchers(springActuatorPaths, UrlPath.OAUTH_PATTERN)
            ;
            http.headers().frameOptions().sameOrigin();

            http.formLogin()
                    .loginPage("/login")
//                    .failureHandler(webAuthenticationFailureHandler)
                    .successHandler(managerAuthSuccessHandler)
//                .and()
//                .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher(StarTigerAdminConstant.UrlPath.LOGOUT, "GET"))
//                .and()
//                .rememberMe()
//                    .key(rememberMeKey)
//                    .rememberMeServices(rememberMeServices)
//                    .authenticationSuccessHandler(webAuthenticationSuccessHandler)
//                .and()
//                .httpBasic()
//                .and().sessionManagement().maximumSessions(1)
            ;

//            if (enableOauth2) {
//                http.oauth2ResourceServer().jwt();
//            }

//            http.exceptionHandling()
//                    .authenticationEntryPoint(webSecurityExceptionHandler)
//                    .accessDeniedHandler(webSecurityExceptionHandler)
            ;
        }
    }
}
