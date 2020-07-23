package com.snake19870227.stiger.admin.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/23
 */
public class InvalidCaptchaException extends AuthenticationException {

    public InvalidCaptchaException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidCaptchaException(String msg) {
        super(msg);
    }
}
