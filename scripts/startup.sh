#!/bin/sh
# Initiated by Jared on May 12, 2017.
# To start the build in tomcat container 9.0

if [ $# != 1 ]; then
  echo "Please specify WORK_DIR firstly"
  exit
fi

WORK_DIR=$1
echo "Working directory is " ${WORK_DIR}
if [ -d ${WORK_DIR}/webapps/services ]; then
  rm -rf ${WORK_DIR}/webapps/services  ${WORK_DIR}/webapps/services.war
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

cp -f /home/repo/ustudy/info-center/target/services.war ${WORK_DIR}/webapps/services.war
if [ $? != 0 ]; then
  echo "Failed to copy infoservice.war into destination directory"
  exit 1
fi
echo "Deploying services.war successfully"

docker run --rm --name infocenter -p 8080:8080 -v ${WORK_DIR}/webapps/:/usr/local/tomcat/webapps \
    -v ${WORK_DIR}/logs/infocenter:/usr/local/tomcat/logs/ -d tomcat:9.0

