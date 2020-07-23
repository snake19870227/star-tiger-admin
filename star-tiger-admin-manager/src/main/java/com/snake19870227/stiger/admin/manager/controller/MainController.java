package com.snake19870227.stiger.admin.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.snake19870227.stiger.core.exception.OptException;
import com.snake19870227.stiger.core.exception.ServiceException;
import com.snake19870227.stiger.web.exception.MvcException;

import static com.snake19870227.stiger.admin.common.StarTigerAdminConstant.UrlPath;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/23
 */
@Controller
public class MainController {

    @GetMapping(path = UrlPath.MAIN)
    public String main() {

        return "main";
    }

    @GetMapping(path = "/aaa")
    public String aaa() {
        try {
            int i = 1/0;

            return "main";
        } catch (Exception e) {
            throw new MvcException("50000", new ServiceException("我是service异常", new OptException("我是opt异常", e)));
        }
    }
}
