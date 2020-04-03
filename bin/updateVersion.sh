#!/bin/bash

if [ ! -n "$1" ]; then
        echo "ERROR: 版本号不存在，请指定参数1"
        exit
fi

mvn versions:set -DnewVersion=$1
mvn versions:commit