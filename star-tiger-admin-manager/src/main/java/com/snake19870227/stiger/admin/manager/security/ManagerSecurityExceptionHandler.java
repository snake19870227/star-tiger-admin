package com.snake19870227.stiger.admin.manager.security;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import com.snake19870227.stiger.admin.common.StarTigerAdminConstant;
import com.snake19870227.stiger.autoconfigure.properties.StarTigerFrameProperties;
import com.snake19870227.stiger.core.StarTigerConstant;
import com.snake19870227.stiger.core.context.StarTigerContext;
import com.snake19870227.stiger.web.restful.RestResp;
import com.snake19870227.stiger.web.utils.WebUtil;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/24
 */
@Component
public class ManagerSecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(ManagerSecurityExceptionHandler.class);

    private static final String AUTH_EXCEPTION_STATUS_CODE = "50002";
    private static final String ACCESS_DENIED_STATUS_CODE = "50100";

    private final StarTigerFrameProperties starTigerFrameProperties;

    public ManagerSecurityExceptionHandler(StarTigerFrameProperties starTigerFrameProperties) {
        this.starTigerFrameProperties = starTigerFrameProperties;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String msg = StarTigerContext.getMessage(StarTigerConstant.StatusCode.PREFIX_CODE + AUTH_EXCEPTION_STATUS_CODE);

        logger.error(msg, authException);

        if (response.isCommitted()) {
            logger.warn("请求响应已被提交");
            return;
        }

        if (starTigerFrameProperties.isUseHttpStatusCode()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        if (WebUtil.isAjaxRequest(request)) {
            RestResp<Object> resp = RestResp.buildResp(AUTH_EXCEPTION_STATUS_CODE, msg);
            ServletUtil.write(
                    response,
                    JSONUtil.toJsonStr(resp),
                    ContentType.build(MediaType.APPLICATION_JSON_VALUE, StandardCharsets.UTF_8)
            );
        } else {
            response.sendRedirect(StarTigerAdminConstant.UrlPath.LOGIN + "?" + StarTigerAdminConstant.UrlParamNames.LOGOUT_SUCCESS);
        }

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        String msg = StarTigerContext.getMessage(StarTigerConstant.StatusCode.PREFIX_CODE + ACCESS_DENIED_STATUS_CODE);

        logger.error(msg, accessDeniedException);

        if (response.isCommitted()) {
            logger.warn("请求响应已被提交");
            return;
        }

        if (starTigerFrameProperties.isUseHttpStatusCode()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        if (WebUtil.isAjaxRequest(request)) {
            RestResp<Object> resp = RestResp.buildResp(ACCESS_DENIED_STATUS_CODE, msg);
            ServletUtil.write(
                    response,
                    JSONUtil.toJsonStr(resp),
                    ContentType.build(MediaType.APPLICATION_JSON_VALUE, StandardCharsets.UTF_8)
            );
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }
}
