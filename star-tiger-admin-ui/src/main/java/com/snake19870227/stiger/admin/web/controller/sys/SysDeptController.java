package com.snake19870227.stiger.admin.web.controller.sys;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.snake19870227.stiger.admin.web.controller.BaseController;
import com.snake19870227.stiger.core.exception.OptException;
import com.snake19870227.stiger.core.exception.ServiceException;
import com.snake19870227.stiger.web.exception.MvcException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/19
 */
@Controller
@RequestMapping(path = "/sys/dept")
public class SysDeptController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysDeptController.class);

    @GetMapping(path = "/main")
    public String main(@RequestParam(name = "menuCode", required = false) String menuCode,
                       Model model, HttpServletRequest request) {
        try {
            int i = 1/0;
            if (StrUtil.isNotBlank(menuCode)) {
                openMenu(menuCode, request);
            }

            return "sys/dept/main";
        } catch (Exception e) {
            throw new MvcException("4010", new ServiceException("我是service异常", new OptException("我是opt异常", e)));
        }

    }
}
