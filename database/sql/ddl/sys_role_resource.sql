drop table if exists sys_role_resource;

create table sys_role_resource
(
    role_res_flow varchar(32) not null comment '角色资源信息流水号',
    role_flow     varchar(32) not null comment '角色流水号',
    res_flow      varchar(32) not null comment '资源流水号',
    delete_flag   varchar(2) default 'N' comment '删除标记',
    primary key (role_res_flow)
);