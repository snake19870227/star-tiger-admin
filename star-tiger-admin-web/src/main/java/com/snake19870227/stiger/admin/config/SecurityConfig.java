package com.snake19870227.stiger.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.StarTigerAdminConstant.UrlPath;
import com.snake19870227.stiger.admin.web.security.WebSecurityExceptionHandler;
import com.snake19870227.stiger.admin.web.security.WebAuthenticationFailureHandler;
import com.snake19870227.stiger.admin.web.security.WebAuthenticationSuccessHandler;

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

//        @Value("${stiger.admin.oauth2.enable:false}")
//        private boolean enableOauth2;

        private final WebAuthenticationSuccessHandler webAuthenticationSuccessHandler;
        private final WebAuthenticationFailureHandler webAuthenticationFailureHandler;

        private final WebSecurityExceptionHandler webSecurityExceptionHandler;

        private final RememberMeServices rememberMeServices;

        public CustomWebSecurityConfigurerAdapter(WebAuthenticationSuccessHandler webAuthenticationSuccessHandler,
                                                  WebAuthenticationFailureHandler webAuthenticationFailureHandler,
                                                  WebSecurityExceptionHandler webSecurityExceptionHandler,
                                                  RememberMeServices rememberMeServices) {
            this.webAuthenticationSuccessHandler = webAuthenticationSuccessHandler;
            this.webAuthenticationFailureHandler = webAuthenticationFailureHandler;
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
                    .failureHandler(webAuthenticationFailureHandler)
                    .successHandler(webAuthenticationSuccessHandler)
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(StarTigerAdminConstant.UrlPath.LOGOUT, "GET"))
                .and()
                .rememberMe()
                    .key(rememberMeKey)
                    .rememberMeServices(rememberMeServices)
                    .authenticationSuccessHandler(webAuthenticationSuccessHandler)
                .and()
                .httpBasic()
//                .and().sessionManagement().maximumSessions(1)
            ;

//            if (enableOauth2) {
//                http.oauth2ResourceServer().jwt();
//            }

            http.exceptionHandling()
                    .authenticationEntryPoint(webSecurityExceptionHandler)
                    .accessDeniedHandler(webSecurityExceptionHandler)
            ;
        }
    }
}
