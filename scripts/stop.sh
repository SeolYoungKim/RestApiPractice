#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)  # stop.sh의 절대경로
ABSDIR=$(dirname $ABSPATH)  # stop.sh의 절대 경로에 해당하는 directory 이름
source ${ABSDIR}/profile.sh  # directory명/profile.sh -> profile.sh의 경로를 찾음

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동 중인 애플리케이션의 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi