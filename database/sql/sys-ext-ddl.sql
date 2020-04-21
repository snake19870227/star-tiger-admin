drop table if exists stigeradmin.sys_ext_client;
create table stigeradmin.sys_ext_client
(
    client_flow                    varchar(32)  not null comment '客户端流水号',
    client_id                      varchar(50)  not null comment '客户端ID',
    client_secret                  varchar(500) not null comment '客户端Secret',
    grant_types                    varchar(100) comment '模式',
    redirect_url                   varchar(500) comment '获取code重定向地址',
    access_token_validity_seconds  int comment '令牌有效期(秒)',
    refresh_token_validity_seconds int comment '刷新令牌有效期(秒)',
    resource_ids                   varchar(50) comment '指定的资源服务器ID',
    delete_flag                    varchar(2) default 'N' comment '删除标记',
    primary key (client_flow)
);

drop table if exists stigeradmin.sys_ext_client_scope;
create table stigeradmin.sys_ext_client_scope
(
    client_scope_flow varchar(32) not null comment '客户端权限范围信息流水号',
    client_flow       varchar(32) not null comment '客户端流水号',
    scope             varchar(50) not null comment '权限范围',
    delete_flag       varchar(2) default 'N' comment '删除标记',
    primary key (client_scope_flow)
);