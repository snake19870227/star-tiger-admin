package com.snake19870227.stiger.admin.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snake19870227.stiger.admin.security.GlobalErrorViewResolver;
import com.snake19870227.stiger.admin.service.sys.SysMenuService;
import com.snake19870227.stiger.admin.service.sys.SysService;
import com.snake19870227.stiger.admin.web.common.WebPostWebErrorHandler;
import com.snake19870227.stiger.admin.web.security.WebAuthenticationFailureHandler;
import com.snake19870227.stiger.admin.web.security.WebAuthenticationSuccessHandler;
import com.snake19870227.stiger.admin.web.security.WebSecurityExceptionHandler;
import com.snake19870227.stiger.autoconfigure.properties.StarTigerFrameProperties;
import com.snake19870227.stiger.web.exception.PostWebErrorHandler;

/**
 * @author Bu HuaYang
 */
@Configuration
public class WebConfig {

    @Bean
    public WebSecurityExceptionHandler webSecurityExceptionHandler(StarTigerFrameProperties starTigerFrameProperties, ObjectMapper objectMapper) {
        return new WebSecurityExceptionHandler(starTigerFrameProperties, objectMapper);
    }

    @Bean
    public WebPostWebErrorHandler postWebErrorHandler() {
        return new WebPostWebErrorHandler();
    }

    @Bean
    public GlobalErrorViewResolver errorViewResolver(ObjectProvider<PostWebErrorHandler> postWebErrorHandlers,
                                                     ApplicationContext applicationContext,
                                                     ResourceProperties resourceProperties) {
        return new GlobalErrorViewResolver(postWebErrorHandlers, applicationContext, resourceProperties);
    }

    @Bean
    public WebAuthenticationSuccessHandler webAuthenticationSuccessHandler(SysService sysService, SysMenuService sysMenuService) {
        return new WebAuthenticationSuccessHandler(sysService, sysMenuService);
    }

    @Bean
    public WebAuthenticationFailureHandler webAuthenticationFailureHandler(StarTigerFrameProperties starTigerFrameProperties) {
        return new WebAuthenticationFailureHandler(starTigerFrameProperties);
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new SpringSessionRememberMeServices();
    }
}
