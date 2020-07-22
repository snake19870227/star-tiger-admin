package com.snake19870227.stiger.admin.manager.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.snake19870227.stiger.admin.common.StarTigerAdminConstant;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/22
 */
@Component
public class ManagerAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public ManagerAuthFailureHandler() {
        super(StarTigerAdminConstant.UrlPath.LOGIN + "?" + StarTigerAdminConstant.UrlParamNames.LOGIN_ERROR);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);
    }
}
