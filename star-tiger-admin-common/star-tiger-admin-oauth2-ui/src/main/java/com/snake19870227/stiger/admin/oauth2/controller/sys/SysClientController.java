package com.snake19870227.stiger.admin.oauth2.controller.sys;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snake19870227.stiger.admin.entity.bo.ClientInfo;
import com.snake19870227.stiger.admin.oauth2.service.SysClientService;
import com.snake19870227.stiger.admin.web.controller.BaseController;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/21
 */
@Controller
@RequestMapping(path = "/sys/ext/oauth2client")
public class SysClientController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysClientController.class);

    private final SysClientService sysClientService;

    public SysClientController(SysClientService sysClientService) {
        this.sysClientService = sysClientService;
    }

    @GetMapping(path = "/main")
    public String main(@RequestParam(name = "menuCode", required = false) String menuCode,
                       HttpServletRequest request) {

        if (StrUtil.isNotBlank(menuCode)) {
            openMenu(menuCode, request);
        }

        return "sys/ext/oauth2client/main";
    }

    @GetMapping(path = "/list")
    public String list(@RequestParam(name = "page", defaultValue = "1") long page,
                       @RequestParam(name = "pageSize", defaultValue = "10") long pageSize,
                       Model model) {
        IPage<ClientInfo> clientInfos = sysClientService.getClientInfo(page, pageSize);
        model.addAttribute("clientInfos", clientInfos);
        return "sys/ext/oauth2client/list";
    }
}
