package com.snake19870227.stiger.admin.enums;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/18
 */
public enum ResourceMethod {

    /**
     * 所有操作范围
     */
    ALL("ALL", "", "全部"),
    /**
     * 查询
     */
    GET("GET", "GET", "查询"),
    /**
     * 新增
     */
    POST("POST", "POST", "新增"),
    /**
     * 修改
     */
    PUT("PUT", "PUT", "修改"),
    /**
     * 删除
     */
    DELETE("DELETE", "DELETE", "删除");

    private final String code;
    private final String value;
    private final String description;

    ResourceMethod(String code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
