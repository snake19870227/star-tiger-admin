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

## 说明
后端管理中心，spring boot 2.x + spring security + AdminLTE  

依赖项目：
```xml
<parent>
    <groupId>com.snake19870227</groupId>
    <artifactId>star-tiger-framework</artifactId>
    <version>0.0.1</version>
</parent>
```
git仓库地址：https://github.com/snake19870227/star-tiger-framework

## 部署
- mysql主从
    1. **在 star-tiger-admin 目录下执行 docker-compose 命令**
        - 将./database/my.cnf分别拷贝至**主从两个mysql容器映射出来的conf.d目录**下  
        - 将./database/sql下的初始化脚本拷贝至**主mysql容器映射出来的init目录**下  
    ```bash
    docker-compose up -d stiger-admin-db-master stiger-admin-db-slave1
    ```
    2. **分别连到两台mysql容器中**
    
    主库：
    ```mysql
    mysql -uroot -p123456
    grant replication slave on *.* to 'slaveuser23306'@'%' identified by '123456';
    flush privileges;
    show master status;
    ```
    记录下'show msater status'的结果，'File'和'Position'的值  
    
    从库：
    ```mysql
    mysql -uroot -p123456
    change master to master_host='mysql13306', master_user='slaveuser23306', \
        master_password='123456', master_port=3306, master_log_file='mysql-bin.000003', \
    master_log_pos=599, master_connect_retry=30;
    start slave;
    ```
    'master_log_file''master_log_pos'分别对应主库'show msater status'的结果，'File'和'Position'的值
    3. **主库初始化数据**
    ```mysql
    source /init/ddl.sql
    source /init/dml.sql
    ```
    4. 从库查看是否已同步数据
    
- redis
    ```bash
    docker-compose up -d stiger-admin-redis
    ```
- 应用
    ```bash
    docker-compose up -d stiger-admin-ui
    ```