package com.snake19870227.stiger.admin.web.controller.sys;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
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
import com.snake19870227.stiger.admin.entity.bo.UserInfo;
import com.snake19870227.stiger.admin.entity.dto.SysUserModel;
import com.snake19870227.stiger.admin.entity.dto.SysUserSearcher;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.admin.service.sys.SysRoleService;
import com.snake19870227.stiger.admin.service.sys.SysUserService;
import com.snake19870227.stiger.admin.web.controller.BaseController;
import com.snake19870227.stiger.web.restful.RestResponse;
import com.snake19870227.stiger.web.restful.RestResponseBuilder;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/19
 */
@Controller
@RequestMapping(path = "/sys/user")
public class SysUserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    private final FindByIndexNameSessionRepository<? extends Session> sessions;

    private final SysObjectMapStruct sysObjectMapStruct;

    private final SysRoleService sysRoleService;

    private final SysUserService sysUserService;

    public SysUserController(FindByIndexNameSessionRepository<? extends Session> sessions,
                             SysObjectMapStruct sysObjectMapStruct, SysRoleService sysRoleService,
                             SysUserService sysUserService) {
        this.sessions = sessions;
        this.sysObjectMapStruct = sysObjectMapStruct;
        this.sysRoleService = sysRoleService;
        this.sysUserService = sysUserService;
    }

    @GetMapping(path = "/main")
    public String main(@RequestParam(name = "menuCode", required = false) String menuCode,
                       Model model, HttpServletRequest request) {

        if (StrUtil.isNotBlank(menuCode)) {
            openMenu(menuCode, request);
        }

        List<SysRole> allRoles = sysRoleService.getAllRoles();
        model.addAttribute("allRoles", allRoles);

        return "sys/user/main";
    }

    @GetMapping(path = "/list")
    public String list(@ModelAttribute SysUserSearcher searcher,
                       @RequestParam(name = "page", defaultValue = "1") long page,
                       @RequestParam(name = "pageSize", defaultValue = "10") long pageSize,
                       Model model) {
        IPage<SysUser> users = sysUserService.searchUsers(searcher, page, pageSize);
        model.addAttribute("sysUsers", users);
        return "sys/user/list";
    }

    @GetMapping(path = "/{userFlow}/info")
    @ResponseBody
    public RestResponse.DefaultRestResponse read(@PathVariable(name = "userFlow") String userFlow) {
        UserInfo userInfo = sysUserService.loadUserInfo(userFlow);
        return RestResponseBuilder.createSuccessDefaultRestResp(userInfo);
    }

    @GetMapping(path = "/checkUsername")
    @ResponseBody
    public boolean checkUsername(@RequestParam(name = "username") String username) {
        Optional<SysUser> user = sysUserService.getUserByUsername(username);
        return !user.isPresent();
    }

    @PostMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse create(@Valid @ModelAttribute SysUserModel userModel,
                                                   @RequestParam(name = "roleFlows") String[] roleFlows) {
        SysUser user = sysObjectMapStruct.toUserPo(userModel);
        sysUserService.createUser(user, roleFlows);
        return RestResponseBuilder.createSuccessDefaultRestResp(user);
    }

    @PutMapping
    @ResponseBody
    public RestResponse.DefaultRestResponse modify(@Valid @ModelAttribute SysUserModel userModel,
                                                   @RequestParam(name = "roleFlows") String[] roleFlows) {
        SysUser user = sysObjectMapStruct.toUserPo(userModel);
        sysUserService.modifyUser(user, roleFlows);
        return RestResponseBuilder.createSuccessDefaultRestResp(user);
    }

    @PutMapping(path = "/{userFlow}/lock")
    @ResponseBody
    public RestResponse.DefaultRestResponse modify(@PathVariable(name = "userFlow") String userFlow,
                                                   @RequestParam(name = "unlocked") boolean unlocked) {
        SysUser user = sysUserService.changeUserLockState(userFlow, unlocked);
        if (user != null) {
            deleteSession(user.getUsername());
            return RestResponseBuilder.createSuccessDefaultRestResp();
        } else {
            return RestResponseBuilder.createFailureDefaultRestResp();
        }
    }

    @PutMapping(path = "/{userFlow}/resetPwd")
    @ResponseBody
    public RestResponse.DefaultRestResponse modify(@PathVariable(name = "userFlow") String userFlow) {
        SysUser user = sysUserService.resetUserPassword(userFlow);
        if (user != null) {
            deleteSession(user.getUsername());
            return RestResponseBuilder.createSuccessDefaultRestResp();
        } else {
            return RestResponseBuilder.createFailureDefaultRestResp();
        }
    }

    @DeleteMapping(path = "/{userFlow}")
    @ResponseBody
    public RestResponse.DefaultRestResponse delete(@PathVariable(name = "userFlow") String userFlow) {
        SysUser user = sysUserService.deleteUser(userFlow);
        if (user != null) {
            deleteSession(user.getUsername());
            return RestResponseBuilder.createSuccessDefaultRestResp();
        } else {
            return RestResponseBuilder.createFailureDefaultRestResp();
        }
    }

    private void deleteSession(String username) {
        try {
            Map<String, ? extends Session> sessionMap = sessions.findByPrincipalName(username);
            if (sessionMap != null) {
                sessionMap.forEach((BiConsumer<String, Session>) (s, session) -> sessions.deleteById(s));
            }
        } catch (Exception e) {
            logger.warn("删除已登录session失败", e);
        }
    }
}
