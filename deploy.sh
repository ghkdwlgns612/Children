#!/bin/bash

export MYSQL_YUBBI_URL="jdbc:mysql://yubbi-database.csebcqevndde.ap-northeast-2.rds.amazonaws.com:3306/yubbi?useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Seoul"
export MYSQL_YUBBI_ID="admin"
export MYSQL_YUBBI_PW="12345678"
export AWS_ACCESS_KEY="AKIAVRKDLKKXQZVZ3SHY"
export AWS_SECRET_KEY="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"

BUILD_JAR=$(ls /home/ec2-user/*.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> build 파일명 : $JAR_NAME"

echo "> 현재 실행중인 애플리케이션 PID"
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다!"
else
  echo "> sudo kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=/home/ec2-user/*.jar
nohup java -jar $DEPLOY_JAR > /dev/null 2> /dev/null < /dev/null &
