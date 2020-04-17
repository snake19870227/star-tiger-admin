#!/bin/bash

docker exec -i adminDbMaster13306 mysql -uroot -p123456 < database/sql/ddl.sql
docker exec -i adminDbMaster13306 mysql -uroot -p123456 < database/sql/dml.sql