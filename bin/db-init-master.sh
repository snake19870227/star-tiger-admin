#!/bin/bash

docker exec -i adminDbMaster13306 mysql -uroot -p123456 -e "grant replication slave on *.* to 'slaver'@'%' identified by '123456'"
docker exec -i adminDbMaster13306 mysql -uroot -p123456 -e "flush privileges"
docker exec -i adminDbMaster13306 mysql -uroot -p123456 -e "show master status\G"