package com.snake19870227.stiger.admin.manager.controller;

import cn.hutool.core.util.StrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.sys.service.ISysRoleService;
import com.snake19870227.stiger.web.restful.RestResp;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/30
 */
@Controller
@RequestMapping(path = "/sys/role")
public class SysRoleController {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    private final ISysRoleService sysRoleService;

    public SysRoleController(ISysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @GetMapping(path = "/main")
    public String main() {
        return "sys/role/main";
    }

    @GetMapping(path = "/data")
    @ResponseBody
    public RestResp<Page<SysRole>> data(@RequestParam(name = "roleCode", required = false) String roleCode,
                                        @RequestParam(name = "roleName", required = false) String roleName,
                                        @RequestParam(name = "page", defaultValue = "1") Long page,
                                        @RequestParam(name = "limit", defaultValue = "10") Long limit) {

        Page<SysRole> pageInfo = new Page<>(page, limit);

        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(roleCode)) {
            queryWrapper.like("role_code", roleCode);
        }

        if (StrUtil.isNotBlank(roleName)) {
            queryWrapper.likeRight("role_name", roleName);
        }

        queryWrapper.orderByAsc("res_path");

        pageInfo = sysRoleService.page(pageInfo, queryWrapper);

        return RestResp.buildResp("10000", pageInfo);
    }
}
