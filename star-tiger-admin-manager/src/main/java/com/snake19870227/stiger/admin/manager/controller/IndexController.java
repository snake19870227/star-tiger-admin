package com.snake19870227.stiger.admin.manager.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.snake19870227.stiger.admin.common.CaptchaCacheStorage;
import com.snake19870227.stiger.admin.common.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.security.UserSecurityDetail;
import com.snake19870227.stiger.core.context.StarTigerContext;

import static com.snake19870227.stiger.admin.common.StarTigerAdminConstant.UrlParamNames;
import static com.snake19870227.stiger.admin.common.StarTigerAdminConstant.UrlPath;
import static com.snake19870227.stiger.admin.common.StarTigerAdminConstant.WebAttrKey;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/17
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${stiger.admin.debug-mode}")
    private boolean debugMode;

    private final CaptchaCacheStorage captchaCacheStorage;

    public IndexController(CaptchaCacheStorage captchaCacheStorage) {
        this.captchaCacheStorage = captchaCacheStorage;
    }

    @GetMapping(path = {"", "/"})
    public String root() {
        return "redirect:" + UrlPath.LOGIN;
    }

    @GetMapping(path = UrlPath.LOGIN)
    public String login(HttpServletRequest request,
                        Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserSecurityDetail userSecurityDetail = (UserSecurityDetail) authentication.getPrincipal();
            if (userSecurityDetail != null) {
                logger.info("用户[{}]已登录", userSecurityDetail.getUsername());
                return "redirect:" + StarTigerAdminConstant.UrlPath.MAIN;
            }
        }

        boolean logoutSuccess = isLogoutSuccess(request);

        boolean loginError = isLoginError(request);

        if (logoutSuccess) {
            model.addAttribute(WebAttrKey.LOGIN_MESSAGE, StarTigerContext.getMessage(StarTigerAdminConstant.MESSAGE_CODE_PREFIX + "10100"));
        }

        HttpSession session = request.getSession(false);

        if (loginError) {

            String errorMsg = "";

            if (session != null) {
                AuthenticationException ex = (AuthenticationException) session
                        .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                errorMsg = ex != null ? ex.getMessage() : "无效的登录凭证";
            }

            if (StrUtil.isNotBlank(errorMsg)) {
                model.addAttribute(
                        WebAttrKey.LOGIN_MESSAGE,
                        StarTigerContext.getMessage(StarTigerAdminConstant.MESSAGE_CODE_PREFIX + "50001", errorMsg)
                );
            }
        }

        return "login";
    }

    @GetMapping(path = UrlPath.CAPTCHA)
    public void captcha(@RequestParam(name = "width", defaultValue = "100") Integer width,
                        @RequestParam(name = "height", defaultValue = "38") Integer height,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        LineCaptcha lineCaptcha;

        if (debugMode) {
            lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, 4, 10);
        } else {
            lineCaptcha = CaptchaUtil.createLineCaptcha(width, height);
        }

        HttpSession session = request.getSession();

        captchaCacheStorage.put(session.getId(), lineCaptcha.getCode());

        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        ServletUtil.write(response, IoUtil.toStream(lineCaptcha.getImageBytes()));

    }

    private boolean isLogoutSuccess(HttpServletRequest request) {
        return matches(request, UrlPath.LOGIN + "?" + UrlParamNames.LOGOUT_SUCCESS);
    }

    private boolean isLoginError(HttpServletRequest request) {
        return matches(request, UrlPath.LOGIN + "?" + UrlParamNames.LOGIN_ERROR);
    }

    private boolean matches(HttpServletRequest request, String url) {
        if (!HttpMethod.GET.matches(request.getMethod()) || url == null) {
            return false;
        }
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            uri = uri.substring(0, pathParamIndex);
        }

        if (request.getQueryString() != null) {
            uri += "?" + request.getQueryString();
        }

        if ("".equals(request.getContextPath())) {
            return uri.equals(url);
        }

        return uri.equals(request.getContextPath() + url);
    }
}
