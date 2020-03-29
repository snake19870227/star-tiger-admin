package com.snake19870227.stiger.admin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author BuHuaYang
 * @date 2019/12/22
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.snake19870227.stiger.admin"
        }
)
public class StarTigerAdminWebApplication {

    private static final Logger logger = LoggerFactory.getLogger(StarTigerAdminWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StarTigerAdminWebApplication.class, args);
    }
}
