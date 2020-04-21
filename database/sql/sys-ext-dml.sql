begin;
set @client1_flow = replace(uuid(), '-', '');
insert into stigeradmin.sys_ext_client (client_flow, client_id, client_secret, grant_types, redirect_url)
 values (@client1_flow, 'client1', '{noop}123456', 'authorization_code,password,refresh_token', 'http://example.com');
insert into stigeradmin.sys_ext_client_scope (client_scope_flow, client_flow, scope)
 values (replace(uuid(), '-', ''), @client1_flow, 'user_base_info');
commit;

begin;
set @order = 200;
set @menu_flow_kfpt = replace(uuid(), '-', '');
set @menu_flow_kfpt_djzh = replace(uuid(), '-', '');
select max(menu_order) into @order from stigeradmin.sys_menu;
insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_order)
values (@menu_flow_kfpt, null, 1, 'kfpt', '开放平台', @order);
set @order = @order + 1;
insert into stigeradmin.sys_menu (menu_flow, parent_menu_flow, menu_level, menu_code, menu_name, menu_path, menu_order)
values (@menu_flow_kfpt_djzh, @menu_flow_kfpt, 2, 'kfpt-djzh', '对接账户', '/sys/ext/oauth2client/main', @order);
commit;

insert into stigeradmin.sys_resource (res_flow, res_name, res_path, res_method)
values (replace(uuid(), '-', ''), '查询客户端', '/sys/ext/oauth2client/**', 'GET'),
       (replace(uuid(), '-', ''), '创建客户端', '/sys/ext/oauth2client', 'POST'),
       (replace(uuid(), '-', ''), '修改客户端', '/sys/ext/oauth2client', 'PUT'),
       (replace(uuid(), '-', ''), '删除客户端', '/sys/ext/oauth2client/*', 'DELETE');
commit;