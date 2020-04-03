package com.snake19870227.stiger.admin.web.config;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.snake19870227.stiger.admin.web.common.MasterSlaveRoutingDataSource;
import com.snake19870227.stiger.admin.web.enums.DBRoutingEnum;

/**
 * @author Bu HuaYang
 */
@Configuration
@AutoConfigureBefore(MybatisPlusAutoConfiguration.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class DatabaseConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource masterSlaveRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                                   @Qualifier("slave1DataSource") DataSource slave1DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DBRoutingEnum.MASTER, masterDataSource);
        targetDataSources.put(DBRoutingEnum.SLAVE1, slave1DataSource);
        MasterSlaveRoutingDataSource myRoutingDataSource = new MasterSlaveRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

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
