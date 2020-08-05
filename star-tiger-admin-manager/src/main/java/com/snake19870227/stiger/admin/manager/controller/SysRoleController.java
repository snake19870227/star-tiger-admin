package com.snake19870227.stiger.admin.manager.controller;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysRoleResource;
import com.snake19870227.stiger.admin.manager.entity.dto.RoleResourceDto;
import com.snake19870227.stiger.admin.sys.service.ISysExtService;
import com.snake19870227.stiger.admin.sys.service.ISysRoleResourceService;
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

    private final ISysRoleResourceService sysRoleResourceService;

    private final ISysExtService sysExtService;

    public SysRoleController(ISysRoleService sysRoleService,
                             ISysRoleResourceService sysRoleResourceService,
                             ISysExtService sysExtService) {
        this.sysRoleService = sysRoleService;
        this.sysRoleResourceService = sysRoleResourceService;
        this.sysExtService = sysExtService;
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

        queryWrapper.orderByAsc("role_name");

        pageInfo = sysRoleService.page(pageInfo, queryWrapper);

        return RestResp.buildResp("10000", pageInfo);
    }

    @GetMapping(path = "/{roleFlow}")
    @ResponseBody
    public RestResp<RoleResourceDto> get(@PathVariable(name = "roleFlow") String roleFlow) {

        SysRole role = sysRoleService.getById(roleFlow);

        QueryWrapper<SysRoleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_flow", roleFlow);
        List<SysRoleResource> roleResources = sysRoleResourceService.list(queryWrapper);
        String[] resourceFlows = roleResources.stream().map(SysRoleResource::getResFlow).toArray(String[]::new);

        RoleResourceDto roleResourceDto = new RoleResourceDto();
        roleResourceDto.setRole(role);
        roleResourceDto.setResourceFlows(resourceFlows);

        return RestResp.buildResp("10000", roleResourceDto);
    }

    @PostMapping
    @ResponseBody
    public RestResp<?> save(@RequestBody RoleResourceDto saveDto) {

        sysExtService.saveRole(saveDto.getRole(), Arrays.asList(saveDto.getResourceFlows()));

        return RestResp.buildResp("10000");
    }
}
