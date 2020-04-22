package com.snake19870227.stiger.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import com.snake19870227.stiger.admin.StarTigerAdminConstant.UrlPath;

/**
 * @author Bu HuaYang
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Configuration
    public static class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Value("${management.endpoints.web.base-path:/actuator}")
        private String springActuatorPath;

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();
            http.headers().frameOptions().deny();

            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();

            urlRegistry
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers(UrlPath.anonymousPaths()).permitAll()
                    .anyRequest().authenticated()
//                    .antMatchers(UrlPath.authenticatedPaths()).authenticated()
//                    .anyRequest().access("@authAssert.canAccess(request, authentication)")
            ;

            http.oauth2ResourceServer().jwt();
        }
    }
}
