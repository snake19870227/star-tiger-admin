drop procedure if exists stigeradmin.init;
create procedure stigeradmin.init()
begin
#     set @root_flow = '00000000000000000000000000000000';
#     set @super_role_flow = '00000000000000000000000000000000';
#     set @super_resource_flow = '00000000000000000000000000000000';

    truncate table sys_resource;
    truncate table sys_role;
    truncate table sys_role_resource;
    truncate table sys_user;
    truncate table sys_user_role;

#     insert into sys_resource (res_flow, res_name, res_path)
#     values (@super_resource_flow, '所有资源', '/**');
    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '系统管理', '/sys/**', null);

    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '机构管理', '/sys/org/**', null);

    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '部门管理', '/sys/dept/**', null);

    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '用户管理', '/sys/user/**', null);

    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '资源管理', '/sys/resource/**', null);
    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '查询资源', '/sys/resource/**', 'GET');
    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '创建资源', '/sys/resource/**', 'POST');
    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '修改资源', '/sys/resource/**', 'PUT');
    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '删除资源', '/sys/resource/**', 'DELETE');

    insert into sys_resource (res_flow, res_name, res_path, res_method)
    values (replace(uuid(), '-', ''), '角色管理', '/sys/role/**', null);

#     insert into sys_role (role_flow, role_code, role_name)
#     values (@super_role_flow, 'super_admin', '超级管理员');

#     insert into sys_role_resource (role_res_flow, role_flow, res_flow)
#     values (replace(uuid(), '-', ''), @super_role_flow, @super_resource_flow);

#     insert into sys_user (user_flow, username, encode_password, short_name)
#     values (@root_flow, 'root', '{noop}123456', '超级管理员');

#     insert into sys_user_role (user_role_flow, user_flow, role_flow)
#     values (replace(uuid(), '-', ''), @root_flow, @super_role_flow);

    set @menu_flow_xtgl = replace(uuid(), '-', '');
    set @menu_flow_zzjg = replace(uuid(), '-', '');
    set @menu_flow_zzjg_jggl = replace(uuid(), '-', '');
    set @menu_flow_zzjg_bmgl = replace(uuid(), '-', '');
    set @menu_flow_zzjg_yhgl = replace(uuid(), '-', '');
    set @menu_flow_qxgl = replace(uuid(), '-', '');
    set @menu_flow_qxgl_zygl = replace(uuid(), '-', '');
    set @menu_flow_qxgl_jsgl = replace(uuid(), '-', '');
    set @menu_flow_gngl = replace(uuid(), '-', '');
    set @menu_flow_gngl_cdgl = replace(uuid(), '-', '');
    set @menu_order = 88;

    truncate table sys_menu;
    truncate table sys_menu_resource;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
    values (@menu_flow_zzjg, null, 1, 'zzjg', '组织架构', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
    values (@menu_flow_zzjg_jggl, @menu_flow_zzjg, 2, 'zzjg-jggl', '机构管理', '/sys/org/main', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
    values (@menu_flow_zzjg_bmgl, @menu_flow_zzjg, 2, 'zzjg-bmgl', '部门管理', '/sys/dept/main', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
    values (@menu_flow_zzjg_yhgl, @menu_flow_zzjg, 2, 'zzjg-yhgl', '用户管理', '/sys/user/main', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
    values (@menu_flow_qxgl, null, 1, 'qxgl', '权限管理', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
    values (@menu_flow_qxgl_zygl, @menu_flow_qxgl, 2, 'qxgl-zygl', '资源管理', '/sys/resource/main', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
    values (@menu_flow_qxgl_jsgl, @menu_flow_qxgl, 2, 'qxgl-jsgl', '角色管理', '/sys/role/main', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
    values (@menu_flow_gngl, null, 1, 'gngl', '功能管理', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
    values (@menu_flow_gngl_cdgl, @menu_flow_gngl, 2, 'gngl-cdgl', '菜单管理', '/sys/menu/main', @menu_order);

    set @menu_order = @menu_order + 1;

    insert into sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
    values (@menu_flow_xtgl, null, 1, 'xtgl', '系统管理', @menu_order);

    set @menu_order = @menu_order + 1;

    commit ;

end;

call stigeradmin.init();

drop procedure if exists stigeradmin.init;