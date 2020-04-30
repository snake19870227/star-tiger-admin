package com.snake19870227.stiger.admin.config;

import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.snake19870227.stiger.admin.monitor.common.OauthTokenHttpHeadersProvider;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/29
 */
@Configuration
public class MonitorConfig {

    private static final Logger logger = LoggerFactory.getLogger(MonitorConfig.class);

    @Bean
    public HttpHeadersProvider oauth2TokenHttpHeadersProvider(RestTemplateBuilder restTemplateBuilder) {
        return new OauthTokenHttpHeadersProvider(restTemplateBuilder);
    }
}
