package com.snake19870227.stiger.admin.manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snake19870227.stiger.admin.common.RestResp;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.sys.service.ISysResourceService;
import com.snake19870227.stiger.web.exception.BaseControllerException;
import com.snake19870227.stiger.web.exception.MvcException;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/24
 */
@Controller
@RequestMapping(path = "/sys/resource")
public class SysResourceController {

    private static final Logger logger = LoggerFactory.getLogger(SysResourceController.class);

    private final ISysResourceService sysResourceService;

    public SysResourceController(ISysResourceService sysResourceService) {
        this.sysResourceService = sysResourceService;
    }

    @GetMapping(path = "/main")
    public String main() {
        return "sys/resource/main";
    }

    @GetMapping(path = "/data")
    @ResponseBody
    public RestResp<Page<SysResource>> data(@RequestParam(name = "page", defaultValue = "1") Long page,
                                            @RequestParam(name = "limit", defaultValue = "10") Long limit) {

        Page<SysResource> pageInfo = new Page<>(page, limit);

        pageInfo = sysResourceService.page(pageInfo);

        return RestResp.buildResp("10000", pageInfo);
    }

    @PutMapping(path = "/enable/{resFlow}/{flag}")
    @ResponseBody
    public RestResp<Object> enable(@PathVariable(name = "resFlow") String resFlow,
                                   @PathVariable(name = "flag") String flag) {
        try {
            SysResource updater = new SysResource();
            updater.setResFlow(resFlow);
            updater.setEnableFlag(flag);
            boolean result = sysResourceService.updateById(updater);
            if (result) {
                return RestResp.buildResp("10000");
            } else {
                return RestResp.buildResp("60001");
            }
        } catch (BaseControllerException e) {
            throw e;
        } catch (Exception e) {
            throw new MvcException("50000", e);
        }
    }
}
