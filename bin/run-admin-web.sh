#!/bin/bash

docker-compose up -d stiger-admin-web

docker exec -id stigerAdminWeb19999 filebeat -e -c /opt/filebeat.yml