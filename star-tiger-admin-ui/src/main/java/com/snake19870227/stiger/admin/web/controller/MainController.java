package com.snake19870227.stiger.admin.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
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

    @GetMapping(path = {StarTigerAdminConstant.UrlPath.ROOT, StarTigerAdminConstant.UrlPath.INDEX})
    public String index() {
        return "redirect:" + StarTigerAdminConstant.UrlPath.LOGIN;
    }

    @GetMapping(path = StarTigerAdminConstant.UrlPath.MAIN)
    public String toMain(HttpServletRequest request) {
        closeAllMenu(request);
        return "main";
    }

    @GetMapping(path = StarTigerAdminConstant.UrlPath.MENU_ROUTING)
    public String menuRouting(@RequestParam(name = "menuCode") String menuCode,
                              RedirectAttributes redirectAttributes) {

        try {
            SysMenu menu = routerService.getRouterMenu(menuCode);

            if (menu == null) {
                throw new MvcException("未找到该功能[menuCode=" + menuCode + "]");
            }

            redirectAttributes.addAttribute("menuCode", menuCode);

            return "redirect:" + menu.getMenuPath();
        } catch (Exception e) {
            throw new MvcException(e);
        }
    }
}
