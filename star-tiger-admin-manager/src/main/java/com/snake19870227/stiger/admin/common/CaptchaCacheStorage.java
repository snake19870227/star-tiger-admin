package com.snake19870227.stiger.admin.common;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/23
 */
public interface CaptchaCacheStorage {

    String CAPTCHA_CACHE = "captcha_cache";

    /**
     * 验证码放入缓存.
     *
     * @param key 验证码存入的key
     * @param code 验证码
     */
    String put(String key, String code);

    /**
     * 从缓存取验证码.
     *
     * @param key 验证码存入的key
     * @return 验证码
     */
    String get(String key);

    /**
     * 验证码手动过期.
     *
     * @param key 验证码存入的key
     */
    void expire(String key);
}
