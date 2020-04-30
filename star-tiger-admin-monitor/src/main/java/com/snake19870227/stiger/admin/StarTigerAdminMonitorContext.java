package com.snake19870227.stiger.admin;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/30
 */
public class StarTigerAdminMonitorContext {

    public static Map<String, String> INSTANCE_ACCESS_TOKENS = new HashMap<>();
    public static Map<String, LocalDateTime> INSTANCE_ACCESS_TOKEN_EXPIRES = new HashMap<>();
}
