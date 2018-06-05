# Written by Jared on Jan 12, 2018.
# Try to replace localhost with real internal ip address and build dashboard, exam modules

#set -x

if [ $# != 4 ]; then
  echo "Please specify proper parameters"
  echo "Usage: build.sh [src dir] [internal host ip] [tracelevel] [prod/dev]"
  exit 1
fi

SRC_DIR=$1
InternalIP=$2
TRACE_LEVEL=$3
OS_NAME=`uname -s`

if [ ! -d ${SRC_DIR} ]; then
  echo "Source directory ${SRC_DIR} not existed"
  exit 1
fi

if [ -d ${SRC_DIR}/dashboard/ ]; then
  cd ${SRC_DIR}/dashboard/
  mvn clean
  if [ $OS_NAME = "Darwin" ]; then
    find . -name "applicationContext.xml" | xargs sed -i "" "s/localhost/${InternalIP}/g"
  else
    find . -name "applicationContext.xml" | xargs sed -i "s/localhost/${InternalIP}/g"
  fi
  mvn eclipse:clean eclipse:eclipse
  mvn clean package -DskipTests
else
  echo "${SRC_DIR}/dashboard/ not existed. Please check again"
  exit 1
fi

if [ -d ${SRC_DIR}/exam/ ]; then
  cd ${SRC_DIR}/exam/
  mvn clean
  if [ $OS_NAME = "Darwin" ]; then  
    # replace ip configurations
    find . -name "applicationContext.xml" | xargs sed -i "" "s/localhost/${InternalIP}/g"
    # setting log levels
    find . -type f -name "log4j2.xml" | xargs sed -i "" "s/USTUDYLOGLEVEL/${TRACE_LEVEL}/g"
  else
    # replace ip configurations
    find . -name "applicationContext.xml" | xargs sed -i "s/localhost/${InternalIP}/g"
    # setting log levels
    find . -type f -name "log4j2.xml" | xargs sed -i "s/USTUDYLOGLEVEL/${TRACE_LEVEL}/g"
  fi
  mvn eclipse:clean eclipse:eclipse
  mvn clean package -DskipTests
else
  echo "${SRC_DIR}/exam/ not existed. Please check again"
  exit 1
fi

# replace nginx to configure host ip address
if [ -d ${SRC_DIR}/dockerfile/nginx/ ]; then
  cd ${SRC_DIR}/dockerfile/nginx/
  if [ $OS_NAME = "Darwin" ]; then
    sed -i "" "s/prodhost/${InternalIP}/g" nginx.conf
  else
    sed -i "s/prodhost/${InternalIP}/g" nginx.conf
  fi
  
  if [ "$4"x = "prod"x ]; then
    sed -i "s/certname/cert\/214560108060077/g" Dockerfile
    sed -i "s/certname/214560108060077/g" nginx.conf
  elif [ "$4"x = "dev"x ]; then
    sed -i "s/certname/cert\/214555228090077/g" Dockerfile
    sed -i "s/certname/214555228090077/g" nginx.conf
  fi 

  docker build --rm -t nginx-ustudy:1.12 .
  if [ $? = 0 ];then
    echo "build nginx image nginx-ustudy:1.12 successfully"
  else
    echo "build nginx image failed"
    exit 1
  fi
else
  echo "nginx.conf not found. Please check again"
  exit 1
fi




