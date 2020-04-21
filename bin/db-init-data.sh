#!/bin/bash

docker exec -i adminDbMaster13306 mysql -uroot -p123456 < database/sql/sys-ddl.sql
docker exec -i adminDbMaster13306 mysql -uroot -p123456 < database/sql/sys-dml.sql

if [ -n "$1" ]; then
  echo "初始化 $1 脚本"
  docker exec -i adminDbMaster13306 mysql -uroot -p123456 < "database/sql/sys-$1-ddl.sql"
  docker exec -i adminDbMaster13306 mysql -uroot -p123456 < "database/sql/sys-$1-dml.sql"
fi
