package com.snake19870227.stiger.admin.web.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.AntPathMatcher;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.entity.bo.MenuInfo;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.service.sys.SysService;
import com.snake19870227.stiger.admin.web.ProjectConstant;
import com.snake19870227.stiger.admin.web.entity.vo.Sidebar;
import com.snake19870227.stiger.core.StarTigerConstant;
import com.snake19870227.stiger.web.utils.WebUtil;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/16
 */
public class WebAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final SysService sysService;

    public WebAuthenticationSuccessHandler(SysService sysService) {
        this.sysService = sysService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        loadUserSidebar(request, authentication);

        if (authentication instanceof RememberMeAuthenticationToken) {
            redirectStrategy.sendRedirect(request, response, WebUtil.getPath(request, false, true));
            clearAuthenticationAttributes(request);
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void loadUserSidebar(HttpServletRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<MenuInfo> menuInfos = sysService.allMenuTree();
        List<MenuInfo> userMenuInfos = new ArrayList<>();
        for (MenuInfo level1 : menuInfos) {
            List<SysMenu> childMenus = level1.getChildMenus().stream().filter(
                    sysMenu -> {
                        for (GrantedAuthority authority : user.getAuthorities()) {
                            String roleCode = StrUtil.replace(authority.getAuthority(), StarTigerConstant.SPRING_SECURITY_ROLE_PREFIX, "");
                            if (StrUtil.equals(StarTigerAdminConstant.SUPER_ROLE_CODE, roleCode)) {
                                return true;
                            }
                            List<SysResource> roleResources = sysService.getResourceByRoleCode(roleCode);
                            if (CollUtil.isNotEmpty(roleResources)) {
                                for (SysResource resource : roleResources) {
                                    AntPathMatcher matcher = new AntPathMatcher();
                                    if (matcher.match(resource.getResPath(), sysMenu.getMenuPath())) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    }
            ).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(childMenus)) {
                MenuInfo userLevel1 = new MenuInfo(level1.getMenu(), childMenus);
                userMenuInfos.add(userLevel1);
            }
        }
        request.getSession().setAttribute(ProjectConstant.WebAttrKey.USER_SIDEBAR, new Sidebar(userMenuInfos));
    }
}
