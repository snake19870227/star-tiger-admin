package com.snake19870227.stiger.admin.web.controller.sys;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.view.ThymeleafView;
import com.snake19870227.stiger.admin.entity.SysObjectMapStruct;
import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.dto.SysMenuModel;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.service.sys.SysMenuService;
import com.snake19870227.stiger.admin.web.controller.BaseController;
import com.snake19870227.stiger.web.restful.RestResponse;
import com.snake19870227.stiger.web.restful.RestResponseBuilder;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/19
 */
@Controller
@RequestMapping(path = "/sys/menu")
public class SysMenuController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

    private final SysObjectMapStruct sysObjectMapStruct;

    private final SysMenuService sysMenuService;

    public SysMenuController(SysObjectMapStruct sysObjectMapStruct, SysMenuService sysMenuService) {
        this.sysObjectMapStruct = sysObjectMapStruct;
        this.sysMenuService = sysMenuService;
    }

    @GetMapping(path = "/main")
    public String main(@RequestParam(name = "menuCode", required = false) String menuCode,
                       Model model, HttpServletRequest request) {

        if (StrUtil.isNotBlank(menuCode)) {
            openMenu(menuCode, request);
        }

        return "sys/menu/main";

    }

    @GetMapping(path = "/tree")
    public String tree(Model model) {
        List<MenuInfo> menuInfos = sysMenuService.allMenuTree();
        model.addAttribute("menuInfos", menuInfos);
        return "sys/menu/tree";
    }

    @GetMapping(path = "/{menuFlow}")
    @ResponseBody
    public RestResponse.DefaultRestResponse read(@PathVariable(name = "menuFlow") String menuFlow) {
        SysMenu menu = sysMenuService.getByMenuFlow(menuFlow);
        return RestResponseBuilder.createSuccessDefaultRestResp(menu);
    }

    @PostMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse create(@Valid @ModelAttribute SysMenuModel menuModel) {
        SysMenu menu = sysObjectMapStruct.toMenuPo(menuModel);
        sysMenuService.create(menu);
        return RestResponseBuilder.createSuccessDefaultRestResp(menu);
    }

    @PutMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse modify(@Valid @ModelAttribute SysMenuModel menuModel) {
        SysMenu menu = sysObjectMapStruct.toMenuPo(menuModel);
        sysMenuService.modify(menu);
        return RestResponseBuilder.createSuccessDefaultRestResp(menu);
    }

    @PutMapping(path = "/swap/{menuFlow1}/{menuFlow2}")
    @ResponseBody
    public RestResponse.DefaultRestResponse swapOrder(@PathVariable(name = "menuFlow1") String menuFlow1,
                                                      @PathVariable(name = "menuFlow2") String menuFlow2) {
        if (sysMenuService.swapOrder(menuFlow1, menuFlow2) != null) {
            return RestResponseBuilder.createSuccessDefaultRestResp();
        }
        return RestResponseBuilder.createFailureDefaultRestResp();
    }

    @DeleteMapping(path = "/{menuFlow}")
    @ResponseBody
    public RestResponse.DefaultRestResponse delete(@PathVariable(name = "menuFlow") String menuFlow) {
        SysMenu menu = sysMenuService.delete(menuFlow);
        return RestResponseBuilder.createSuccessDefaultRestResp(menu);
    }

}
