package com.snake19870227.stiger.admin.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.snake19870227.stiger.admin.common.CaptchaCacheStorage;
import com.snake19870227.stiger.admin.common.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.manager.security.ManagerAuthFailureHandler;
import com.snake19870227.stiger.admin.manager.security.ManagerAuthSuccessHandler;
import com.snake19870227.stiger.admin.security.ImageCaptchaAuthenticationFilter;

import static com.snake19870227.stiger.admin.common.StarTigerAdminConstant.UrlPath;

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

        private final ManagerAuthFailureHandler managerAuthFailureHandler;

        private final CaptchaCacheStorage captchaCacheStorage;

        public CustomWebSecurityConfigurerAdapter(ManagerAuthSuccessHandler managerAuthSuccessHandler,
                                                  ManagerAuthFailureHandler managerAuthFailureHandler,
                                                  CaptchaCacheStorage captchaCacheStorage) {
            this.managerAuthSuccessHandler = managerAuthSuccessHandler;
            this.managerAuthFailureHandler = managerAuthFailureHandler;
            this.captchaCacheStorage = captchaCacheStorage;
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
                    .antMatchers("/aaa", "/aaaa", "/api/**").permitAll()
//                    .antMatchers(StarTigerAdminConstant.UrlPath.authenticatedPaths()).authenticated()
//                    .anyRequest().access("@authAssert.canAccess(request, authentication)")
                    .anyRequest().authenticated()
            ;

            http.csrf()
//                    .ignoringAntMatchers(springActuatorPaths, UrlPath.OAUTH_PATTERN)
            ;
            http.headers().frameOptions().sameOrigin();

            http.formLogin()
                    .loginPage(UrlPath.LOGIN)
                    .failureHandler(managerAuthFailureHandler)
                    .successHandler(managerAuthSuccessHandler)
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher(UrlPath.LOGOUT, HttpMethod.GET.name()))
//                .and()
//                .rememberMe()
//                    .key(rememberMeKey)
//                    .rememberMeServices(rememberMeServices)
//                    .authenticationSuccessHandler(webAuthenticationSuccessHandler)
//                .and()
//                .httpBasic()
                .and()
                .addFilterBefore(
                        new ImageCaptchaAuthenticationFilter(UrlPath.LOGIN, captchaCacheStorage, managerAuthFailureHandler),
                        UsernamePasswordAuthenticationFilter.class
                )
                .sessionManagement().maximumSessions(1)
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
