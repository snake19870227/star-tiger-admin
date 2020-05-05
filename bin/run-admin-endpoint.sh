#!/bin/bash

docker-compose up -d stiger-admin-endpoint

docker exec -id stigerAdminEndpoint19998 filebeat -e -c /opt/filebeat.yml