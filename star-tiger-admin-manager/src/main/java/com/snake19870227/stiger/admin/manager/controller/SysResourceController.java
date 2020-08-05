package com.snake19870227.stiger.admin.manager.controller;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snake19870227.stiger.admin.common.layui.TransferData;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.sys.service.ISysExtService;
import com.snake19870227.stiger.admin.sys.service.ISysResourceService;
import com.snake19870227.stiger.core.StarTigerConstant;
import com.snake19870227.stiger.core.context.StarTigerContext;
import com.snake19870227.stiger.core.exception.BusinessException;
import com.snake19870227.stiger.web.exception.BaseControllerException;
import com.snake19870227.stiger.web.exception.MvcException;
import com.snake19870227.stiger.web.restful.RestResp;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/07/24
 */
@Controller
@RequestMapping(path = "/sys/resource")
public class SysResourceController {

    private static final Logger logger = LoggerFactory.getLogger(SysResourceController.class);

    private final ISysResourceService sysResourceService;

    private final ISysExtService sysExtService;

    public SysResourceController(ISysResourceService sysResourceService,
                                 ISysExtService sysExtService) {
        this.sysResourceService = sysResourceService;
        this.sysExtService = sysExtService;
    }

    @GetMapping(path = "/main")
    public String main() {
        return "sys/resource/main";
    }

    @GetMapping(path = "/data")
    @ResponseBody
    public RestResp<Page<SysResource>> data(@RequestParam(name = "resName", required = false) String resName,
                                            @RequestParam(name = "resPath", required = false) String resPath,
                                            @RequestParam(name = "resMethod", required = false) String resMethod,
                                            @RequestParam(name = "enableFlag", required = false) String enableFlag,
                                            @RequestParam(name = "page", defaultValue = "1") Long page,
                                            @RequestParam(name = "limit", defaultValue = "10") Long limit) {

        Page<SysResource> pageInfo = new Page<>(page, limit);

        QueryWrapper<SysResource> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(resName)) {
            queryWrapper.like("res_name", resName);
        }

        if (StrUtil.isNotBlank(resPath)) {
            queryWrapper.likeRight("res_path", resPath);
        }

        if (StrUtil.isNotBlank(resMethod)) {
            queryWrapper.eq("res_method", resMethod);
        }

        if (StrUtil.isNotBlank(enableFlag)) {
            queryWrapper.eq("enable_flag", enableFlag);
        }

        queryWrapper.orderByAsc("res_path");

        pageInfo = sysResourceService.page(pageInfo, queryWrapper);

        return RestResp.buildResp("10000", pageInfo);
    }

    @GetMapping(path = "/transferData")
    @ResponseBody
    public RestResp<List<TransferData>> get(@RequestParam(name = "roleFlow", required = false) String roleFlow) {

        Map<String, String> roleResourceMap = new HashMap<>();
        if (StrUtil.isNotBlank(roleFlow)) {
            List<String> roleResourceFlows = sysExtService.getResourceFlowsByRole(roleFlow);
            roleResourceFlows.forEach(s -> roleResourceMap.put(s, s));
        }

        QueryWrapper<SysResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enable_flag", StarTigerConstant.FLAG_Y);
        queryWrapper.orderByAsc("res_name");
        List<SysResource> resources = sysResourceService.list(queryWrapper);
        List<TransferData> transferDataList =
                resources.stream().map(resource -> {
                    TransferData transferData = new TransferData();
                    transferData.setValue(resource.getResFlow());
                    transferData.setTitle(resource.getResName());
                    transferData.setChecked(roleResourceMap.containsKey(resource.getResFlow()));
                    transferData.setDisabled(false);
                    return transferData;
                }).collect(Collectors.toList());

        return RestResp.buildResp("10000", transferDataList);
    }

    @GetMapping(path = "/{resFlow}")
    @ResponseBody
    public RestResp<SysResource> recordInfo(@PathVariable(name = "resFlow") String resFlow) {

        SysResource resource = sysResourceService.getById(resFlow);

        if (resource == null) {
            throw new BusinessException(StarTigerContext.getMessage("resource.notfound"));
        }

        return RestResp.buildResp("10000", resource);
    }

    @PostMapping
    @ResponseBody
    public RestResp<SysResource> add(@RequestBody SysResource resource) {

        sysResourceService.save(resource);

        return RestResp.buildResp("10000", resource);
    }

    @PutMapping(path = "/{resFlow}")
    @ResponseBody
    public RestResp<SysResource> update(@PathVariable(name = "resFlow") String resFlow,
                                        @RequestBody SysResource resource) {

        sysResourceService.updateById(resource);

        return RestResp.buildResp("10000", resource);
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
