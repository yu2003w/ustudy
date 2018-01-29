#!/bin/sh
# Initiated by Jared on Dec 25, 2017.
# To start dashboard in separate tomcat container 9.0

if [ $# != 2 ]; then
  echo "Please specify WORK_DIR SOURCE_DIR firstly"
  echo "Usage: startdashboard.sh [work_dir] [source_dir]"
  exit
fi

echo "Stopping already started containers"
docker stop dredis dashboard

WORK_DIR=$1
SOURCE_DIR=$2
echo "Working directory is " ${WORK_DIR}
echo "Source directory is " ${SOURCE_DIR}
if [ -d ${WORK_DIR}/dashboard/webapps/dashboard ]; then
  rm -rf ${WORK_DIR}/dashboard/webapps/dashboard  ${WORK_DIR}/dashboard/webapps/dashboard.war
  if [ $? != 0 ]; then
    echo "Failed to delete deployed dashboard.war"
    exit 1
  fi
else
  mkdir -p ${WORK_DIR}/dashboard/webapps/
  if [ $? != 0 ]; then
    echo "Failed to create directory" ${WORK_DIR}/dashboard/webapps/
    exit 1
  fi
fi

cp -f ${SOURCE_DIR}/ustudy/dashboard/target/dashboard.war ${WORK_DIR}/dashboard/webapps/dashboard.war
if [ $? != 0 ]; then
  echo "Failed to copy dashboard.war into destination directory"
  exit 1
fi
echo "Deploying dashboard.war successfully"

# start redis cache for dashboard module, each tomcat had one redis instance
docker run --rm -it --name dredis -p 6380:6379 -v ${WORK_DIR}/dashboard/redis:/data -d redis:3.2
if [ $? != 0 ];then
  echo "Failed to launch redis container"
  exit 1
else
  echo "Launched redis container successfully"
fi

# before launching tomcat, clear logs generated last time
echo "clear logs generated in ${WORK_DIR}/logs/dashboard/"
rm ${WORK_DIR}/logs/dashboard/*

docker run --rm --name dashboard -p 8081:8080 -v ${WORK_DIR}/dashboard/webapps/:/usr/local/tomcat/webapps \
    -v ${WORK_DIR}/logs/dashboard:/usr/local/tomcat/logs/ -d tomcat:9.0

# deploy front end code 
while [ ! -d ${WORK_DIR}/dashboard/webapps/dashboard/ ]
do
  echo "waiting for deploying frontend for dashboard"
  sleep 10
done
cp -rf /mnt/repo/ustudy-console/dist/* ${WORK_DIR}/dashboard/webapps/dashboard/

