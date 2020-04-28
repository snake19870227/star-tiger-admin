#!/bin/bash

help(){
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./build.sh [args...]"
  echo ""
  echo "-updateVersion   更新版本号 eg: ./build.sh updateVersion 0.0.1"
  echo "-packdev         打包开发环境"
  echo "-packdevdy       打包开发环境，读写分离方式"
  echo "-packdocker      打包docker环境"
  echo "-packdockerdy    打包docker环境，读写分离方式"
  echo "-db-master       构建主数据库 eg: ./build.sh db-master"
  echo "-db-slave        构建从数据库 eg: ./build.sh db-slave 1"
  echo "-db-init-master  初始化主数据库 eg: ./build.sh db-init-master"
  echo "-db-init-slave   初始化从数据库 eg: ./build.sh db-init-slave \"mysql-bin.000003\" 591"
  echo "-db-init-data    初始化数据 eg: ./build.sh db-init-data ext"
  echo ""
  echo "--------------------------------------------------------------------------"
}

case "$1" in
  'updateVersion')
    bin/updateVersion.sh "$2"
	;;
  'packdev')
    bin/package.sh dev
	;;
  'packdevdy')
    bin/package.sh devdynamic
	;;
  'packdocker')
    bin/package.sh docker
	;;
  'packdockerdy')
    bin/package.sh dockerdynamic
	;;
  'db-master')
    bin/db-master.sh
	;;
  'db-slave')
    bin/db-slave.sh "$2"
	;;
  'db-init-master')
    bin/db-init-master.sh
	;;
  'db-init-slave')
    bin/db-init-slave.sh "$2" "$3" "$4"
	;;
  'db-init-data')
    bin/db-init-data.sh "$2"
	;;
  *)
    help
esac