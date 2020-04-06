#!/bin/bash

docker-compose build "stiger-admin-db-slave$1"
docker-compose up -d "stiger-admin-db-slave$1"
