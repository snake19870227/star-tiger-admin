drop table if exists sys_module;

create table sys_module
(
    module_flow  varchar(32) not null comment '模块流水号',
    module_name  varchar(50) not null comment '模块名称',
    module_order int comment '排序码',
    delete_flag  varchar(2) default 'N' comment '删除标记',
    primary key (module_flow)
);