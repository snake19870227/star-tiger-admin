#!/bin/bash

docker exec -i "$1" mysql -uroot -p123456 -e \
  "change master to master_host='mysql13306', master_user='slaver', master_password='123456', master_port=3306, master_log_file='$2', master_log_pos=$3, master_connect_retry=30"
docker exec -i "$1" mysql -uroot -p123456 -e "start slave"
docker exec -i "$1" mysql -uroot -p123456 -e "show slave status\G"