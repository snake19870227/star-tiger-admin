#!/bin/bash

help() {
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./build.sh 打包并发布docker镜像"
  echo ""
  echo "--------------------------------------------------------------------------"
}

mvn clean package -Pdocker

#docker-compose build
docker-compose up -d

#artifactId="star-tiger-admin-ui"
#adminUiPath="./$artifactId"
#
#while read line; do
#  if [[ $line == version* ]]; then
#    version=${line#*=}
#  fi
#done < "$adminUiPath/target/maven-archiver/pom.properties"
#
#if [[ -n $version ]]; then
#  imageName=$artifactId
#  echo "镜像名称: $imageName"
#  echo "构建版本: $version"
#  jarFile="$adminUiPath/target/$artifactId-$version.jar"
#  if [[ -f $jarFile ]]; then
#    echo "jar文件: $jarFile"
#    containerName="$imageName-$version-test"
#    echo "测试容器: $containerName"
#    docker build --build-arg "jar_file=$jarFile" --build-arg "spring_prifiles_active=docker" -t "$imageName" -t "$imageName:$version" ./star-tiger-admin-ui
#  fi
#fi
