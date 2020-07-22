package com.snake19870227.stiger.admin.manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.snake19870227.stiger.admin.common.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.security.UserSecurityDetail;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/17
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping(path = {"", "/"})
    public String root() {
        return "redirect:" + StarTigerAdminConstant.UrlPath.LOGIN;
    }

    @GetMapping(path = StarTigerAdminConstant.UrlPath.LOGIN)
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserSecurityDetail userSecurityDetail = (UserSecurityDetail) authentication.getPrincipal();
            if (userSecurityDetail != null) {
                logger.info("用户[{}]已登录", userSecurityDetail.getUsername());
                return "redirect:" + StarTigerAdminConstant.UrlPath.MAIN;
            }
        }
        return "login";
    }

    @GetMapping(path = "/main")
    public String main() {
        return "main";
    }
}
