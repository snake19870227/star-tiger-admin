package com.snake19870227.stiger.admin.entity;

import cn.hutool.core.util.StrUtil;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.snake19870227.stiger.admin.StarTigerAdminConstant;
import com.snake19870227.stiger.admin.entity.dto.SysResModel;
import com.snake19870227.stiger.admin.entity.dto.SysRoleModel;
import com.snake19870227.stiger.admin.entity.dto.SysUserModel;
import com.snake19870227.stiger.admin.entity.po.SysResource;
import com.snake19870227.stiger.admin.entity.po.SysRole;
import com.snake19870227.stiger.admin.entity.po.SysUser;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * @date 2020/03/24
 */
@Mapper(
        componentModel = "spring",
        imports = {
                StrUtil.class,
                StarTigerAdminConstant.class
        }
)
public interface SysObjectMapStruct {

    /**
     * 创建、修改资源的前端请求对象，转化为数据对象<br>
     * <br>
     * 其中 {@link SysResModel} 的属性 {@code resMethod} <br>
     * 会调用枚举方法 {@link StarTigerAdminConstant.ResourceMethod#getValue()} 方法获取真实值
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
                    "StarTigerAdminConstant.ResourceMethod.valueOf(po.getResMethod()) " +
                    ": " +
                    "StarTigerAdminConstant.ResourceMethod.ALL" +
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
}
