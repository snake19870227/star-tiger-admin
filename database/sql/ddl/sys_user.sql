drop table if exists sys_user;

create table sys_user
(
    user_flow        varchar(32) not null comment '用户流水号',
    username         varchar(50) not null comment '用户登录名',
    encode_password  varchar(500) comment '用户登录密码',
    short_name       varchar(50) comment '短名称(用于显示)',
    locked           varchar(2) default 'N' comment '是否锁定',
    expire_date_time varchar(2) comment '到期时间',
    expired          varchar(2) default 'N' comment '是否过期',
    delete_flag      varchar(2) default 'N' comment '删除标记',
    primary key (user_flow)
);