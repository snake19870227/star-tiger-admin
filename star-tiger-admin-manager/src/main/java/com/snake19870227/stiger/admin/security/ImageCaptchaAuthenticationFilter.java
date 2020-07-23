package com.snake19870227.stiger.admin.security;

import cn.hutool.core.util.StrUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import com.snake19870227.stiger.admin.common.CaptchaCacheStorage;
import com.snake19870227.stiger.core.context.StarTigerContext;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/23
 */
public class ImageCaptchaAuthenticationFilter extends GenericFilterBean {

    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private final CaptchaCacheStorage captchaCacheStorage;

    private final RequestMatcher requiresAuthenticationRequestMatcher;

    private final AuthenticationFailureHandler failureHandler;

    public ImageCaptchaAuthenticationFilter(String loginUrl,
                                            CaptchaCacheStorage captchaCacheStorage,
                                            AuthenticationFailureHandler failureHandler) {
        this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(loginUrl, HttpMethod.POST.name());
        this.captchaCacheStorage = captchaCacheStorage;
        this.failureHandler = failureHandler;
    }

    public ImageCaptchaAuthenticationFilter(RequestMatcher requestMatcher,
                                            CaptchaCacheStorage captchaCacheStorage,
                                            AuthenticationFailureHandler failureHandler) {
        this.requiresAuthenticationRequestMatcher = requestMatcher;
        this.captchaCacheStorage = captchaCacheStorage;
        this.failureHandler = failureHandler;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!requiresAuthenticationRequestMatcher.matches(request)) {
            chain.doFilter(request, response);
            return;
        }

        String captchaParam = request.getParameter(SPRING_SECURITY_FORM_CAPTCHA_KEY);

        HttpSession session = request.getSession(false);

        if (session != null) {
            String code = captchaCacheStorage.get(session.getId());

            if (StrUtil.equals(code, captchaParam)) {
                captchaCacheStorage.expire(session.getId());
                chain.doFilter(request, response);
                return;
            }
        }

        failureHandler.onAuthenticationFailure(request, response,
                new InvalidCaptchaException(StarTigerContext.getMessage("captcha.error")));
    }
}
