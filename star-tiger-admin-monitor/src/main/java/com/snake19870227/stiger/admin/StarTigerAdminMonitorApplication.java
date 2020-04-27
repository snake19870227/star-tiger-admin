package com.snake19870227.stiger.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/26
 */
@SpringBootApplication
@EnableAdminServer
public class StarTigerAdminMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarTigerAdminMonitorApplication.class, args);
    }
}
