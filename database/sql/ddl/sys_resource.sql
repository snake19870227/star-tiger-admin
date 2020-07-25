drop table if exists sys_resource;

create table sys_resource
(
    res_flow    varchar(32)  not null comment '资源流水号',
    res_name    varchar(50)  not null comment '资源名称',
    res_path    varchar(500) not null comment '资源路径',
    res_method  varchar(50) comment '操作范围',
    enable_flag varchar(2) default 'Y' comment '启用标记',
    delete_flag varchar(2) default 'N' comment '删除标记',
    primary key (res_flow)
);