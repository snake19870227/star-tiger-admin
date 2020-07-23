package com.snake19870227.stiger.admin.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.snake19870227.stiger.admin.common.CaptchaCacheStorage;
import com.snake19870227.stiger.autoconfigure.properties.StarTigerFrameProperties;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/22
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(StarTigerFrameProperties.class)
public class GlobalConfig {

    @Bean
    public CaptchaCacheStorage captchaCacheStorage() {
        return new CaptchaCacheStorage() {

            @CachePut(cacheNames = CaptchaCacheStorage.CAPTCHA_CACHE, key = "#key")
            @Override
            public String put(String key, String code) {
                return code;
            }

            @Cacheable(cacheNames = CaptchaCacheStorage.CAPTCHA_CACHE, key = "#key")
            @Override
            public String get(String key) {
                return null;
            }

            @CacheEvict(cacheNames = CaptchaCacheStorage.CAPTCHA_CACHE, key = "#key")
            @Override
            public void expire(String key) {

            }
        };
    }
}
