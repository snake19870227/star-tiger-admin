package com.snake19870227.stiger.admin.web.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;

/**
 * @author Bu HuaYang
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DatabaseConfig {

    @Configuration
    @AutoConfigureAfter(MybatisPlusAutoConfiguration.class)
    @MapperScan(basePackages = {
            "com.snake19870227.stiger.admin.dao.mapper"
    })
    public static class MybatisConfig {

        @Bean
        public PaginationInterceptor paginationInterceptor() {
            PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
            paginationInterceptor.setOverflow(false);
            paginationInterceptor.setLimit(500);
            paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
            return paginationInterceptor;
        }

    }
}
