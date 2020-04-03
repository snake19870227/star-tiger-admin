#!/bin/bash

if [ ! -n "$1" ]; then
        echo "ERROR: 新版本不存在，请指定参数1"
        exit
fi

mvn versions:set -DnewVersion=$1