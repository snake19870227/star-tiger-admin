CREATE SCHEMA if not exists `stigeradmin` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

drop table if exists stigeradmin.sys_cfg;
create table stigeradmin.sys_cfg
(
    cfg_code  varchar(50) not null comment '配置代码',
    cfg_value varchar(4000) comment '配置内容',
    primary key (cfg_code)
);

drop table if exists stigeradmin.sys_org;
create table stigeradmin.sys_org
(
    org_flow varchar(32) not null comment '机构流水号',
    org_code varchar(50) comment '机构代码',
    org_name varchar(500) comment '机构名称',
    primary key (org_flow)
);

drop table if exists stigeradmin.sys_dept;
create table stigeradmin.sys_dept
(
    dept_flow varchar(32) not null comment '部门流水号',
    org_flow  varchar(32) not null comment '机构流水号',
    dept_code varchar(50) comment '部门代码',
    dept_name varchar(500) comment '部门名称',
    primary key (dept_flow)
);

drop table if exists stigeradmin.sys_user;
create table stigeradmin.sys_user
(
    user_flow       varchar(32) not null comment '用户流水号',
    username        varchar(50) not null comment '用户登录名',
    encode_password varchar(500) comment '用户登录密码',
    short_name      varchar(50) comment '短名称(用于显示)',
    locked          varchar(2) default 'N' comment '是否锁定',
    primary key (user_flow)
);

drop table if exists stigeradmin.sys_user_subject;
create table stigeradmin.sys_user_subject
(
    subject_flow varchar(32) not null comment '用户隶属信息流水号',
    org_flow     varchar(32) not null comment '机构流水号',
    dept_flow    varchar(32) not null comment '部门流水号',
    user_flow    varchar(32) not null comment '用户流水号',
    primary key (subject_flow)
);

drop table if exists stigeradmin.sys_role;
create table stigeradmin.sys_role
(
    role_flow varchar(32) not null comment '角色流水号',
    role_code varchar(50) comment '角色代码',
    role_name varchar(50) comment '角色名称',
    primary key (role_flow)
);

drop table if exists stigeradmin.sys_user_role;
create table stigeradmin.sys_user_role
(
    user_role_flow varchar(32) not null comment '用户角色信息流水号',
    user_flow      varchar(32) comment '用户流水号',
    role_flow      varchar(32) comment '角色流水号',
    primary key (user_role_flow)
);

drop table if exists stigeradmin.sys_resource;
create table stigeradmin.sys_resource
(
    res_flow   varchar(32)  not null comment '资源流水号',
    res_name   varchar(50)  not null comment '资源名称',
    res_path   varchar(500) not null comment '资源路径',
    res_method varchar(50) comment '操作范围',
    primary key (res_flow)
);

drop table if exists stigeradmin.sys_role_resource;
create table stigeradmin.sys_role_resource
(
    role_res_flow varchar(32) not null comment '角色资源信息流水号',
    role_flow     varchar(32) not null comment '角色流水号',
    res_flow      varchar(32) not null comment '资源流水号',
    primary key (role_res_flow)
);

drop table if exists stigeradmin.sys_menu;
create table stigeradmin.sys_menu
(
    menu_flow        varchar(32) not null comment '菜单流水号',
    parent_menu_flow varchar(32) comment '父级菜单流水号',
    menu_level       int comment '菜单层级',
    menu_code        varchar(50) comment '菜单代码',
    menu_name        varchar(50) comment '菜单名称',
    menu_path        varchar(500) comment '菜单地址',
    menu_order       int comment '排序码',
    primary key (menu_flow)
);

drop table if exists stigeradmin.sys_menu_resource;
create table stigeradmin.sys_menu_resource
(
    menu_res_flow varchar(32) not null comment '菜单资源信息流水号',
    menu_flow     varchar(32) not null comment '菜单流水号',
    res_flow      varchar(32) not null comment '资源流水号',
    primary key (menu_res_flow)
);