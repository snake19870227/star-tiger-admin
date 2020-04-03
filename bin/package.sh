#!/bin/bash

if [ ! -n "$1" ]; then
        echo "ERROR: 打包环境[profile]不存在，请指定参数1"
        exit
fi

mvn clean package -P $1