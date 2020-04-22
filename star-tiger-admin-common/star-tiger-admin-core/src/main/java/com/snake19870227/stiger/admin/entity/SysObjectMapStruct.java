package com.snake19870227.stiger.admin.entity;

import cn.hutool.core.util.StrUtil;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.snake19870227.stiger.admin.entity.dto.SysMenuModel;
import com.snake19870227.stiger.admin.entity.dto.SysResModel;
import com.snake19870227.stiger.admin.entity.dto.SysRoleModel;
import com.snake19870227.stiger.admin.entity.dto.SysUserModel;
import com.snake19870227.stiger.admin.entity.po.SysMenu;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;
import com.snake19870227.stiger.admin.enums.ResourceMethod;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/03/24
 */
@Mapper(
        componentModel = "spring",
        imports = {
                StrUtil.class,
                ResourceMethod.class
        }
)
public interface SysObjectMapStruct {

    /**
     * 创建、修改资源的前端请求对象，转化为数据对象<br>
     * <br>
     * 其中 {@link SysResModel} 的属性 {@code resMethod} <br>
     * 会调用枚举方法 {@link ResourceMethod#getValue()} 方法获取真实值
     *
     * @param model 创建、修改资源的前端请求对象
     * @return 数据对象 {@link SysResource}
     */
    @Mapping(target = "resFlow", source = "resFlow")
    @Mapping(target = "resName", source = "resName")
    @Mapping(target = "resPath", source = "resPath")
    @Mapping(target = "resMethod",
            expression = "java(model != null ? model.getResMethod().getValue() : \"\")")
    SysResource toResourcePo(SysResModel model);

    @Mapping(target = "resFlow", source = "resFlow")
    @Mapping(target = "resName", source = "resName")
    @Mapping(target = "resPath", source = "resPath")
    @Mapping(
            target = "resMethod",
            expression = "java(StrUtil.isNotBlank(po.getResMethod()) ? " +
                    "ResourceMethod.valueOf(po.getResMethod()) " +
                    ": " +
                    "ResourceMethod.ALL" +
                    ")"
    )
    SysResModel toResourceModel(SysResource po);

    @Mapping(target = "roleFlow", source = "roleFlow")
    @Mapping(target = "roleCode", source = "roleCode")
    @Mapping(target = "roleName", source = "roleName")
    SysRole toRolePo(SysRoleModel model);

    @Mapping(target = "userFlow", source = "userFlow")
    @Mapping(target = "shortName", source = "shortName")
    SysUser toUserPo(SysUserModel model);

    @Mapping(target = "menuFlow", source = "menuFlow")
    @Mapping(target = "parentMenuFlow", source = "parentMenuFlow")
    @Mapping(target = "menuLevel", source = "menuLevel")
    @Mapping(target = "menuCode", source = "menuCode")
    @Mapping(target = "menuName", source = "menuName")
    @Mapping(target = "menuPath", source = "menuPath")
    SysMenu toMenuPo(SysMenuModel model);
}
