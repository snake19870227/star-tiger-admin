package com.snake19870227.stiger.admin.web.controller.sys;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.snake19870227.stiger.admin.entity.SysObjectMapStruct;
import com.snake19870227.stiger.admin.entity.bo.RoleInfo;
import com.snake19870227.stiger.admin.entity.dto.SysRoleModel;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.service.sys.SysRoleService;
import com.snake19870227.stiger.admin.web.controller.BaseController;
import com.snake19870227.stiger.web.restful.RestResponse;
import com.snake19870227.stiger.web.restful.RestResponseBuilder;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/19
 */
@Controller
@RequestMapping(path = "/sys/role")
public class SysRoleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    private final SysObjectMapStruct sysObjectMapStruct;

    private final SysRoleService sysRoleService;

    public SysRoleController(SysObjectMapStruct sysObjectMapStruct, SysRoleService sysRoleService) {
        this.sysObjectMapStruct = sysObjectMapStruct;
        this.sysRoleService = sysRoleService;
    }

    @GetMapping(path = "/main")
    public String main(@RequestParam(name = "menuCode", required = false) String menuCode,
                       Model model, HttpServletRequest request) {

        if (StrUtil.isNotBlank(menuCode)) {
            openMenu(menuCode, request);
        }

        return "sys/role/main";

    }

    @GetMapping(path = "/all")
    @ResponseBody
    public RestResponse.DefaultRestResponse all() {
        List<SysRole> allRoles = sysRoleService.getAllRoles();
        return RestResponseBuilder.createSuccessDefaultRestResp(allRoles);
    }

    @GetMapping(path = "/list")
    public String list(@RequestParam(name = "searchCode", required = false) String searchCode,
                       @RequestParam(name = "searchName", required = false) String searchName,
                       @RequestParam(name = "searchResName", required = false) String searchResName,
                       @RequestParam(name = "page", defaultValue = "1") long page,
                       @RequestParam(name = "pageSize", defaultValue = "10") long pageSize,
                       Model model) {
        IPage<SysRole> roles = sysRoleService.searchRoles(searchCode, searchName, searchResName, page, pageSize);
        model.addAttribute("sysRoles", roles);
        return "sys/role/list";
    }

    @GetMapping(path = "/{roleFlow}/info")
    @ResponseBody
    public RestResponse.DefaultRestResponse read(@PathVariable(name = "roleFlow") String roleFlow) {
        RoleInfo roleInfo = sysRoleService.readRoleInfo(roleFlow);
        return RestResponseBuilder.createSuccessDefaultRestResp(roleInfo);
    }

    @GetMapping(path = "/checkRoleCode")
    @ResponseBody
    public boolean checkRoleCode(@RequestParam(name = "roleCode") String roleCode) {
        Optional<SysRole> role = sysRoleService.readRoleByRoleCode(roleCode);
        return !role.isPresent();
    }

    @PostMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse create(@Valid @ModelAttribute SysRoleModel roleModel,
                                                   @RequestParam(name = "resFlows") String[] resFlows) {
        SysRole role = sysObjectMapStruct.toRolePo(roleModel);
        sysRoleService.createRole(role, resFlows);
        return RestResponseBuilder.createSuccessDefaultRestResp(role);
    }

    @PutMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse modify(@Valid @ModelAttribute SysRoleModel roleModel,
                                                   @RequestParam(name = "resFlows") String[] resFlows) {
        SysRole role = sysObjectMapStruct.toRolePo(roleModel);
        sysRoleService.modifyRole(role, resFlows);
        return RestResponseBuilder.createSuccessDefaultRestResp(role);
    }

    @DeleteMapping(path = "/{roleFlow}")
    @ResponseBody
    public RestResponse.DefaultRestResponse delete(@PathVariable(name = "roleFlow") String roleFlow) {
        sysRoleService.deleteRole(roleFlow);
        return RestResponseBuilder.createSuccessDefaultRestResp();
    }
}
