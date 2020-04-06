SET character_set_client = utf8;
SET character_set_results = utf8;
SET character_set_connection = utf8;

begin;
insert into stigeradmin.sys_resource (res_flow, res_name, res_path, res_method)
values (replace(uuid(), '-', ''), '查询菜单', '/sys/menu/**', 'GET'),
       (replace(uuid(), '-', ''), '创建菜单', '/sys/menu', 'POST'),
       (replace(uuid(), '-', ''), '修改菜单', '/sys/menu', 'PUT'),
       (replace(uuid(), '-', ''), '删除菜单', '/sys/menu/*', 'DELETE'),
       (replace(uuid(), '-', ''), '排序菜单', '/sys/menu/swap/**', 'PUT'),
       (replace(uuid(), '-', ''), '查询资源', '/sys/resource/**', 'GET'),
       (replace(uuid(), '-', ''), '创建资源', '/sys/resource', 'POST'),
       (replace(uuid(), '-', ''), '修改资源', '/sys/resource', 'PUT'),
       (replace(uuid(), '-', ''), '删除资源', '/sys/resource/*', 'DELETE'),
       (replace(uuid(), '-', ''), '查询角色', '/sys/role/**', 'GET'),
       (replace(uuid(), '-', ''), '创建角色', '/sys/role', 'POST'),
       (replace(uuid(), '-', ''), '修改角色', '/sys/role', 'PUT'),
       (replace(uuid(), '-', ''), '删除角色', '/sys/role/*', 'DELETE'),
       (replace(uuid(), '-', ''), '查询用户', '/sys/user/**', 'GET'),
       (replace(uuid(), '-', ''), '创建用户', '/sys/user', 'POST'),
       (replace(uuid(), '-', ''), '修改用户', '/sys/user', 'PUT'),
       (replace(uuid(), '-', ''), '删除用户', '/sys/user/*', 'DELETE'),
       (replace(uuid(), '-', ''), '锁定用户', '/sys/user/*/lock', 'PUT'),
       (replace(uuid(), '-', ''), '重置密码', '/sys/user/*/resetPwd', 'PUT');
commit;

begin;
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

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
values (@menu_flow_zzjg, null, 1, 'zzjg', '组织架构', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
values (@menu_flow_zzjg_jggl, @menu_flow_zzjg, 2, 'zzjg-jggl', '机构管理', '/sys/org/main', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
values (@menu_flow_zzjg_bmgl, @menu_flow_zzjg, 2, 'zzjg-bmgl', '部门管理', '/sys/dept/main', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
values (@menu_flow_zzjg_yhgl, @menu_flow_zzjg, 2, 'zzjg-yhgl', '用户管理', '/sys/user/main', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
values (@menu_flow_qxgl, null, 1, 'qxgl', '权限管理', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
values (@menu_flow_qxgl_zygl, @menu_flow_qxgl, 2, 'qxgl-zygl', '资源管理', '/sys/resource/main', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
values (@menu_flow_qxgl_jsgl, @menu_flow_qxgl, 2, 'qxgl-jsgl', '角色管理', '/sys/role/main', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
values (@menu_flow_gngl, null, 1, 'gngl', '功能管理', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
values (@menu_flow_gngl_cdgl, @menu_flow_gngl, 2, 'gngl-cdgl', '菜单管理', '/sys/menu/main', @menu_order);

set @menu_order = @menu_order + 1;

insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
values (@menu_flow_xtgl, null, 1, 'xtgl', '系统管理', @menu_order);

commit;