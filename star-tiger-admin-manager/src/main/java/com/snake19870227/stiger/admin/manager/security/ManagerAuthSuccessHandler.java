package com.snake19870227.stiger.admin.manager.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.snake19870227.stiger.admin.sys.service.ISysExtService;
import com.snake19870227.stiger.web.utils.WebUtil;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/16
 */
@Component
public class ManagerAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(ManagerAuthSuccessHandler.class);

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final ISysExtService sysExtService;

    public ManagerAuthSuccessHandler(ISysExtService sysExtService) {
        this.sysExtService = sysExtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if (authentication instanceof RememberMeAuthenticationToken) {
            redirectStrategy.sendRedirect(request, response, WebUtil.getPath(request, false, true));
            clearAuthenticationAttributes(request);
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
