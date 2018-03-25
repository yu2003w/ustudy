#!/bin/sh
# Initiated by Jared on March 22, 2018.
# To start dashboard in separate tomcat container 9.0

if [ $# != 2 ]; then
  echo "Please specify WORK_DIR SOURCE_DIR firstly"
  echo "Usage: startmmadapter.sh [work_dir] [source_dir]"
  exit
fi

echo "Stopping already started containers"
docker stop mmadapter

WORK_DIR=$1
SOURCE_DIR=$2
echo "Working directory is " ${WORK_DIR}
echo "Source directory is " ${SOURCE_DIR}
if [ -d ${WORK_DIR}/mmadapter/webapps/mmadapter ]; then
  rm -rf ${WORK_DIR}/mmadapter/webapps/mmadapter  ${WORK_DIR}/mmadapter/webapps/mmadapter.war
  if [ $? != 0 ]; then
    echo "Failed to delete deployed mmadapter.war"
    exit 1
  fi
else
  mkdir -p ${WORK_DIR}/mmadapter/webapps/
  if [ $? != 0 ]; then
    echo "Failed to create directory" ${WORK_DIR}/mmadapter/webapps/
    exit 1
  fi
fi

cp -f ${SOURCE_DIR}/ustudy/mmadapter/target/mmadapter.war ${WORK_DIR}/mmadapter/webapps/mmadapter.war
if [ $? != 0 ]; then
  echo "Failed to copy mmadapter.war into destination directory"
  exit 1
fi
echo "Deploying mmadapter.war successfully"

# before launching tomcat, clear logs generated last time
echo "clear logs generated in ${WORK_DIR}/logs/mmadapter/"
rm ${WORK_DIR}/logs/mmadapter/*

docker run --rm --name mmadapter -p 8082:8080 -v ${WORK_DIR}/mmadapter/webapps/:/usr/local/tomcat/webapps \
    -v ${WORK_DIR}/logs/mmadapter:/usr/local/tomcat/logs/ -d tomcat:9.0

if [ $? = 0 ]; then
  echo "mmadapter started successfully"
fi

