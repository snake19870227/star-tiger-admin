# Star Tiger Admin
```bash
                                    ,
                     A           \  :  /           A
                 ___/_\___    `. __/ \__ .'    ___/_\___
                  ',. ..'     _ _\     /_ _     ',. ..'
        A         /.'^'.\        /_   _\        /.'^'.\         A
    ___/_\___    /'     '\     .'  \ /  `.     /'     '\    ___/_\___
     ',. ..'                     /  :  \                     ',. ..'
     /.'^'.\                                                 /.'^'.\
    /'     '\              ___......----:'"":--....(\       /'     '\
                    .-':'"":   :  :  :   :  :  :.(1\.`-.
                  .'`.  `.  :  :  :   :   : : : : : :  .';
                 :-`. :   .  : :  `.  :   : :.   : :`.`. a;
                 : ;-. `-.-._.  :  :   :  ::. .' `. `., =  ;
                 :-:.` .-. _-.,  :  :  : ::,.'.-' ;-. ,'''"
               .'.' ;`. .-' `-.:  :  : : :;.-'.-.'   `-'
        :.   .'.'.-' .'`-.' -._;..:---'''"~;._.-;
        :`--'.'  : :'     ;`-.;            :.`.-'`.
         `'"`    : :      ;`.;             :=; `.-'`.
                 : '.    :  ;              :-:   `._-`.
                  `'"'    `. `.            `--'     `._;
                            `'"'
```
由 https://github.com/snake19870227/StarTiger/tree/master/StarTiger-admin 迁移至此  

### 说明
后端管理中心，spring boot 2.x + spring security + AdminLTE  

依赖项目：
```xml
<parent>
    <groupId>com.snake19870227</groupId>
    <artifactId>star-tiger-framework</artifactId>
    <version>${lastVersion}</version>
</parent>
```
git仓库地址：https://github.com/snake19870227/star-tiger-framework

### 部署准备
1. 授权脚本文件
    ```bash
    chmod u+x build.sh
    chmod u+x bin/*
    ```

2. `com.snake19870227:star-tiger-framework` 已发布至中央仓库，因此无需部署nexus私仓

~~2. 本地部署nexus~~  

~~3. 发布`star-tiger-framework`至本地nexus私仓~~

### 普通部署
1. 数据库与初始化数据

    - 本机安装mysql
    - 初始化数据  
    `mysql -uroot -p123456 < /init/ddl.sql`  
    `mysql -uroot -p123456 < /init/dml.sql`

2. redis  
   本机安装redis

3. 配置文件与启动程序
   - 修改`star-tiger-admin-runtime`模块下`application-dev.yml`关于数据库连接与redis连接的配置
   - 在根目录[`star-tiger-admin`]下执行`./build.sh packdev`进行打包
   - 执行`java -jar ./star-tiger-admin-runtime/target/StarTigerAdminRuntime.jar`启动程序

### Docker部署
1. mysql主从部署及配置

    - 构建主从数据库镜像并创建容器
        ```bash
        build.sh db-master
        build.sh db-slave 1
        ```
    其中第二行命令的第二个参数为从数据库索引，即`docker-compose.yml`中前缀为`stiger-admin-db-slave`的从库配置，  
    构建`stiger-admin-db-slave2`则命令为`build.sh db-slave 2`
    
    - 主从配置
        ```bash
        build.sh db-init-master
        build.sh db-init-slave adminDbSlave123306 mysql-bin.000004 591
        ```
        第二行命令参数:   
        - `db-init-slave` 执行从库配置  
        - `mysql23306` 从库容器名，`docker-compose.yml`中配置  
        - `mysql-bin.000004`与`591` 主库`show master status`输出结果（第一行命令会默认执行输出）`File`与`Position`
    
    - 初始化数据
        ```bash
        build.sh db-init-data
        ```
    
2. redis
    ```bash
    docker-compose up -d stiger-admin-redis
    ```
3. 应用
    ```bash
    docker-compose build stiger-admin-runtime-dynamic
    docker-compose up -d stiger-admin-runtime-dynamic
    ```