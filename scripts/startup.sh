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

if [ -d ${WORK_DIR}/ustudy/webapps/ROOT/ ]; then
  rm -rf ${WORK_DIR}/ustudy/webapps/ROOT/  ${WORK_DIR}/ustudy/webapps/ROOT.war
  if [ $? != 0 ]; then
    echo "Failed to delete deployed war"
    exit 1
  fi
else
  mkdir -p ${WORK_DIR}/ustudy/webapps/
  if [ $? != 0 ]; then
    echo "Failed to create directory" ${WORK_DIR}/ustudy/webapps/
    exit 1
  fi
fi

cp -f ${SOURCE_DIR}/ustudy/exam/target/ROOT.war ${WORK_DIR}/ustudy/webapps/ROOT.war
if [ $? != 0 ]; then
  echo "Failed to copy war of examination into destination directory"
  exit 1
fi
echo "Deploying exam.war successfully"

# before launching tomcat, clear logs generated last time
echo "clear logs generated in ${WORK_DIR}/ustudy/logs/"
rm ${WORK_DIR}/logs/ustudy/*

docker run --rm --name ustudy -p 8080:8080 -v ${WORK_DIR}/ustudy/webapps/:/usr/local/tomcat/webapps \
    -v ${WORK_DIR}/logs/ustudy/:/usr/local/tomcat/logs/ -d tomcat:9.0

