package com.snake19870227.stiger.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import com.snake19870227.stiger.admin.security.CustomUserDetailsManager;
import com.snake19870227.stiger.admin.service.sys.SysUserService;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/23
 */
@Configuration
public class GlobalSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsManager userDetailsManager(SysUserService sysUserService) {
        return new CustomUserDetailsManager(sysUserService);
    }
}
