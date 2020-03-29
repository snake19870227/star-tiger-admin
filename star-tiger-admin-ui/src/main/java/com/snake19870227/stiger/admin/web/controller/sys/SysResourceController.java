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
import com.snake19870227.stiger.admin.entity.SysObjectMapStruct;
import com.snake19870227.stiger.admin.entity.bo.RecordPage;
import com.snake19870227.stiger.admin.entity.dto.SysResModel;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.service.sys.SysService;
import com.snake19870227.stiger.admin.web.controller.BaseController;
import com.snake19870227.stiger.web.restful.RestResponse;
import com.snake19870227.stiger.web.restful.RestResponseBuilder;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/17
 */
@Controller
@RequestMapping(path = "/sys/resource")
public class SysResourceController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysResourceController.class);

    private final SysService sysService;

    private final SysObjectMapStruct sysObjectMapStruct;

    public SysResourceController(SysService sysService, SysObjectMapStruct sysObjectMapStruct) {
        this.sysService = sysService;
        this.sysObjectMapStruct = sysObjectMapStruct;
    }

    @GetMapping(path = "/main")
    public String main(@RequestParam(name = "menuCode", required = false) String menuCode,
                       Model model, HttpServletRequest request) {

        if (StrUtil.isNotBlank(menuCode)) {
            openMenu(menuCode, request);
        }

        return "sys/resource/main";
    }

    @GetMapping(path = "/list")
    public String list(@RequestParam(name = "searchName", required = false) String searchName,
                       @RequestParam(name = "page", defaultValue = "1") long page,
                       @RequestParam(name = "pageSize", defaultValue = "10") long pageSize,
                       Model model) {
        RecordPage<SysResource> resources = sysService.searchResources(searchName, page, pageSize);
        if (logger.isDebugEnabled()) {
            logger.debug("共{}页,共{}条记录,当前第{}页", resources.getPages(), resources.getTotal(), resources.getCurrent());
        }
        model.addAttribute("sysResources", resources);
        return "sys/resource/list";
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public RestResponse.DefaultRestResponse all() {
        List<SysResource> resources = sysService.getAllResource();
        return RestResponseBuilder.createSuccessDefaultRestResp(resources);
    }

    @GetMapping(path = "/{resFlow}")
    @ResponseBody
    public RestResponse.DefaultRestResponse read(@PathVariable(name = "resFlow") String resFlow) {
        SysResource resource = sysService.readResource(resFlow);
        return RestResponseBuilder.createSuccessDefaultRestResp(resource);
    }

    @PostMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse create(@Valid @ModelAttribute SysResModel modal) {
        SysResource resource = sysObjectMapStruct.toResourcePo(modal);
        sysService.createResource(resource);
        return RestResponseBuilder.createSuccessDefaultRestResp(resource);
    }

    @PutMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse modify(@Valid @ModelAttribute SysResModel modal) {
        SysResource resource = sysObjectMapStruct.toResourcePo(modal);
        sysService.modifyResource(resource);
        return RestResponseBuilder.createSuccessDefaultRestResp(resource);
    }

    @DeleteMapping(path = "/{resFlow}")
    @ResponseBody
    public RestResponse.DefaultRestResponse delete(@PathVariable(name = "resFlow") String resFlow) {
        sysService.deleteResource(resFlow);
        return RestResponseBuilder.createSuccessDefaultRestResp();
    }
}
