drop table if exists sys_cfg;

create table sys_cfg
(
    cfg_code    varchar(50) not null comment '配置代码',
    cfg_value   varchar(4000) null comment '配置内容',
    org_flow    varchar(32) null comment '机构流水号',
    delete_flag varchar(2) default 'N' comment '删除标记',
    primary key (cfg_code)
);