package com.snake19870227.stiger.admin.manager.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.snake19870227.stiger.admin.manager.common.StarTigerAdminManagerConstant.WebAttrKey.MULTI_MODULE;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/25
 */
@Configuration
public class InitConfig {

    @Value("${stiger.admin.init.multi-module}")
    private boolean multiModule;

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setAttribute(MULTI_MODULE, multiModule);
            }
        };
    }
}
