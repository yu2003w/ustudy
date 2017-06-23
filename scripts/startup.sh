#!/bin/sh
# Initiated by Jared on May 12, 2017.
# To start the build in tomcat container 9.0

if [ $# != 2 ]; then
  echo "Please specify WORK_DIR SOURCE_DIR firstly"
  echo "Usage: startup.sh [work_dir] [source_dir]"
  exit
fi

WORK_DIR=$1
SOURCE_DIR=$2
echo "Working directory is " ${WORK_DIR}
echo "Source directory is " ${SOURCE_DIR}
if [ -d ${WORK_DIR}/webapps/dashboard ]; then
  rm -rf ${WORK_DIR}/webapps/dashboard  ${WORK_DIR}/webapps/dashboard.war
  if [ $? != 0 ]; then
    echo "Failed to delete deployed war"
    exit 1
  fi
else
  mkdir -p ${WORK_DIR}/webapps/
  if [ $? != 0 ]; then
    echo "Failed to create directory" ${WORK_DIR}/webapps/
    exit 1
  fi
fi

cp -f ${SOURCE_DIR}/ustudy/dashboard/target/dashboard.war ${WORK_DIR}/webapps/dashboard.war
if [ $? != 0 ]; then
  echo "Failed to copy dashboard.war into destination directory"
  exit 1
fi
echo "Deploying dashboard.war successfully"

if [ -d ${WORK_DIR}/webapps/infocen ]; then
  rm -rf ${WORK_DIR}/webapps/infocen  ${WORK_DIR}/webapps/infocen.war
  if [ $? != 0 ]; then
    echo "Failed to delete deployed war"
    exit 1
  fi
else
  mkdir -p ${WORK_DIR}/webapps/
  if [ $? != 0 ]; then
    echo "Failed to create directory" ${WORK_DIR}/webapps/
    exit 1
  fi
fi

cp -f ${SOURCE_DIR}/ustudy/infocen/target/infocen.war ${WORK_DIR}/webapps/infocen.war
if [ $? != 0 ]; then
  echo "Failed to copy infoservice.war into destination directory"
  exit 1
fi
echo "Deploying infocen.war successfully"


docker run --rm --name ustudy -p 8080:8080 -v ${WORK_DIR}/webapps/:/usr/local/tomcat/webapps \
    -v ${WORK_DIR}/logs/ustudy:/usr/local/tomcat/logs/ -d tomcat:9.0

