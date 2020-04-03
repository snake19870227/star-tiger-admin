package com.snake19870227.stiger.admin.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/04/04
 */
public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(MasterSlaveRoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
