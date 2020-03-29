package com.snake19870227.stiger.admin.web.controller;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import com.snake19870227.stiger.admin.web.ProjectConstant;
import com.snake19870227.stiger.core.context.StarTigerContext;
import com.snake19870227.stiger.web.StarTigerWebConstant;

/**
 * @author Bu HuaYang
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ModelAttribute
    public void loginMessage(HttpServletRequest request, Model model) {
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String errorMessage = null;
            if (StrUtil.equals(ProjectConstant.UrlParamNames.LOGIN_ERROR, paramName)) {
                errorMessage = StarTigerContext.getMessage("code.1001");
            }
            if (StrUtil.equals(ProjectConstant.UrlParamNames.LOGIN_EXPIRE, paramName)) {
                errorMessage = StarTigerContext.getMessage("code.2003");
            }
            if (StrUtil.equals(ProjectConstant.UrlParamNames.LOGIN_LOCKED, paramName)) {
                errorMessage = StarTigerContext.getMessage("code.2004");
            }
            model.addAttribute(StarTigerWebConstant.ViewAttrKey.ERROR_MESSAGE, errorMessage);
        }
    }

    @GetMapping(path = ProjectConstant.UrlPath.LOGIN)
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            if (user != null) {
                logger.info("用户[{}]已登录", user.getUsername());
                return "redirect:" + ProjectConstant.UrlPath.MAIN;
            }
        }
        return "login";
    }

    @GetMapping(path = ProjectConstant.UrlPath.ACCESS_DENIED)
    public String accessDenied(@RequestAttribute(name =StarTigerWebConstant.ViewAttrKey.ACCESS_DENIED_URL) String accessDeniedUrl,
                               Model model) {
        model.addAttribute(StarTigerWebConstant.ViewAttrKey.ERROR_MESSAGE, accessDeniedUrl);
        return StarTigerWebConstant.ViewName.ERROR_403;
    }
}
