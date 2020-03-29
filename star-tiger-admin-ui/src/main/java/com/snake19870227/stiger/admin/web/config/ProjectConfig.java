package com.snake19870227.stiger.admin.web.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.snake19870227.stiger.admin.web.common.GlobalErrorViewResolver;
import com.snake19870227.stiger.admin.web.common.WebPostWebErrorHandler;
import com.snake19870227.stiger.web.exception.PostWebErrorHandler;

/**
 * @author Bu HuaYang
 */
@Configuration
@EnableCaching
public class ProjectConfig {

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
}
