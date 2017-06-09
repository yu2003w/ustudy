#!/bin/sh
# Initiated by Jared on May 12, 2017.
# To start the build in tomcat container 9.0

if [ $# != 1 ]; then
  echo "Please specify WORK_DIR firstly"
  exit
fi

WORK_DIR=$1
echo "Working directory is " ${WORK_DIR}
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

cp -f /home/repo/ustudy/adminboard/target/dashboard.war ${WORK_DIR}/webapps/dashboard.war
if [ $? != 0 ]; then
  echo "Failed to copy dashboard.war into destination directory"
  exit 1
fi
echo "Deploying dashboard.war successfully"

docker run --rm --name dashboard -p 8080:8080 -v ${WORK_DIR}/webapps/:/usr/local/tomcat/webapps \
    -v ${WORK_DIR}/logs/dashboard:/usr/local/tomcat/logs/ -d tomcat:9.0

