package com.snake19870227.stiger.admin.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.entity.bo.ResourceInfo;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.service.sys.SysService;
import com.snake19870227.stiger.core.StarTigerConstant;
import com.snake19870227.stiger.web.utils.WebUtil;

/**
 * @author Bu HuaYang
 */
@Component
public class AuthAssert {

    private static final Logger logger = LoggerFactory.getLogger(AuthAssert.class);

    private RoleVoter roleVoter = new RoleVoter();

    private final SysService sysService;

    public AuthAssert(SysService sysService) {
        this.sysService = sysService;
    }

    public boolean canAccess(HttpServletRequest request, Authentication authentication) {

        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        User user = (User) authentication.getPrincipal();

        logger.debug("开始验证[{}]是否可访问[{}]", user.getUsername(), request.getServletPath());

        List<SysResource> allResourceList = sysService.getAllResource();

        if (CollUtil.isEmpty(allResourceList)) {
            return false;
        }

        List<SysRole> matchedRoleList = new ArrayList<>();
        allResourceList.stream()
                .filter(resource -> {
                    AntPathRequestMatcher matcher = new AntPathRequestMatcher(resource.getResPath(), resource.getResMethod());
                    return matcher.matches(request);
                }).forEach(resource -> {
                    ResourceInfo resourceInfo = sysService.loadResourceInfo(resource.getResFlow());
                    if (resourceInfo.getRoles() != null) {
                        matchedRoleList.addAll(resourceInfo.getRoles());
                    }
                });

        matchedRoleList.add(StarTigerAdminConstant.getSuperRole());

        String[] roles = matchedRoleList.stream()
                .map(sysRole -> StarTigerConstant.SPRING_SECURITY_ROLE_PREFIX + sysRole.getRoleCode())
                .toArray(String[]::new);


        if (ArrayUtil.isEmpty(roles)) {
            logger.error("未找到访问[{}]所需的角色", WebUtil.getPath(request, false, false));
            return false;
        }

        int result = roleVoter.vote(authentication, null, SecurityConfig.createList(roles));

        logger.debug("验证[{}]是否可访问[{} {}],结果:{}", user.getUsername(), request.getMethod(), request.getServletPath(), result);

        return AccessDecisionVoter.ACCESS_DENIED != result;
    }
}
