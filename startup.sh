#!/bin/sh
# Initiated by Jared on May 12, 2017.
# To test the build in tomcat container 9.0
rm -rf /home/jared/bench/services
if [ $? != 0 ]; then
  echo "Failed to delete deployed war"
  exit 1
fi

cp -f /home/repo/ustudy/info-center/target/services.war /home/jared/bench/services.war
if [ $? != 0 ]; then
  echo "Failed to copy infoservice.war into destination directory"
  exit 1
fi
docker run -it --rm -p 8080:8080 -v /home/jared/bench/:/usr/local/tomcat/webapps tomcat:9.0
