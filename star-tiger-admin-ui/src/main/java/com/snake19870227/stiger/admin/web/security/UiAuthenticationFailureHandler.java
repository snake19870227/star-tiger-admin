package com.snake19870227.stiger.admin.web.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.autoconfigure.properties.StarTigerFrameProperties;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class UiAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(UiAuthenticationFailureHandler.class);

    private final StarTigerFrameProperties starTigerFrameProperties;

    public UiAuthenticationFailureHandler(StarTigerFrameProperties starTigerFrameProperties) {
        this.starTigerFrameProperties = starTigerFrameProperties;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.error("登录失败[{}]", exception.getLocalizedMessage(), exception);
        if (starTigerFrameProperties.isUseHttpStatusCode()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        String urlParam = null;
        if (exception instanceof CredentialsExpiredException) {
            urlParam = StarTigerAdminConstant.UrlParamNames.LOGIN_EXPIRE;
        } else if (exception instanceof LockedException) {
            urlParam = StarTigerAdminConstant.UrlParamNames.LOGIN_LOCKED;
        } else {
            urlParam = StarTigerAdminConstant.UrlParamNames.LOGIN_ERROR;
        }
        response.sendRedirect(StarTigerAdminConstant.UrlPath.LOGIN + "?" + urlParam);
    }
}
