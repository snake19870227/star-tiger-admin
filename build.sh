#!/bin/bash

help(){
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./build.sh [args...]"
  echo ""
  echo "-updateVersion    更新版本号 eg: ./build.sh updateVersion 0.0.1"
  echo "-packdev          打包开发环境"
  echo "-packdevdy        打包开发环境，读写分离方式"
  echo "-packdocker       打包docker环境"
  echo "-packdockerdy     打包docker环境，读写分离方式"
  echo ""
  echo "--------------------------------------------------------------------------"
}

case "$1" in
  'updateVersion')
    bin/updateVersion.sh $2
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
  *)
    help
esac