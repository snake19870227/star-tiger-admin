package com.snake19870227.stiger.admin.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.snake19870227.stiger.admin.web.enums.DBRoutingEnum;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/04/03
 */
public class DBContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(DBContextHolder.class);

    private static final ThreadLocal<DBRoutingEnum> contextHolder = new ThreadLocal<>();

    public static void set(DBRoutingEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBRoutingEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBRoutingEnum.MASTER);
        logger.debug("切换到master数据源");
    }

    public static void slave() {
        set(DBRoutingEnum.SLAVE1);
        logger.debug("切换到slave1数据源");
    }
}
