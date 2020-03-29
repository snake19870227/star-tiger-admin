package com.snake19870227.stiger.admin.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.web.ProjectConstant;
import com.snake19870227.stiger.admin.web.service.RouterService;
import com.snake19870227.stiger.web.exception.MvcException;

/**
 * @author Bu HuaYang
 */
@Controller
public class MainController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final RouterService routerService;

    public MainController(RouterService routerService) {
        this.routerService = routerService;
    }

    @GetMapping(path = {ProjectConstant.UrlPath.ROOT, ProjectConstant.UrlPath.INDEX})
    public String index() {
        return "redirect:" + ProjectConstant.UrlPath.LOGIN;
    }

    @GetMapping(path = ProjectConstant.UrlPath.MAIN)
    public String toMain(HttpServletRequest request) {
        closeAllMenu(request);
        return "main";
    }

    @GetMapping(path = ProjectConstant.UrlPath.MENU_ROUTING)
    public String menuRouting(@RequestParam(name = "menuCode") String menuCode,
                              RedirectAttributes redirectAttributes) {

        SysMenu menu = routerService.getRouterMenu(menuCode);

        if (menu == null) {
            throw new MvcException("未找到该功能[menuCode=" + menuCode + "]");
        }

        redirectAttributes.addAttribute("menuCode", menuCode);

        return "redirect:" + menu.getMenuPath();
    }
}
