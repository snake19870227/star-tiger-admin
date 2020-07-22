package com.snake19870227.stiger.admin.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.snake19870227.stiger.autoconfigure.properties.StarTigerFrameProperties;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/22
 */
@Configuration
@EnableConfigurationProperties(StarTigerFrameProperties.class)
public class GlobalConfig {
}
