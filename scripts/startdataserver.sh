#!/bin/sh
# Initiated by Jared on May, 2017.
#
# Start mysql container to host data
#

if [ $# != 2 ]; then
  echo "Please specify WORK_DIR SCHEMA_DIR firstly"
  echo "Usage: startmysql.sh [work_dir] [schema_dir]"
  exit 1
fi
WORK_DIR=$1
SCHEMA_DIR=$2
OS_NAME=`uname -s`

echo "Using ${WORK_DIR} as working directory"
if [ ! -d ${WORK_DIR}/mysql/schema/ ]; then
  mkdir -p ${WORK_DIR}/mysql/schema/ 
  if [ $? != 0 ]; then
    echo "Failed to create schema directory" ${WORK_DIR}/mysql/schema/
    exit 1
  else
    echo "Created schema directory" ${WORK_DIR}/mysql/schema/
  fi
else
  # need to clean schema directory firstly
  rm -rf ${WORK_DIR}/mysql/schema/*
  if [ $? != 0 ];then
    echo "Failed to clean schemas loaded previously"
    exit 1
  else
    echo "clean previously loaded schemas successfully"
  fi
fi

cp -Rf ${SCHEMA_DIR} ${WORK_DIR}/mysql/
if [ $? != 0 ]; then
  echo "Failed to copy schema files into " ${WORK_DIR}/mysql/schema/
  exit 1
else
  echo "Copy schema files into " ${WORK_DIR}/mysql/schema/ " successfully"
fi

# permission needed for MacOS
if [ $OS_NAME = "Darwin" ]; then
  chmod -R 777 ${WORK_DIR}/mysql
else
  chown -R 999:999 ${WORK_DIR}/mysql/
fi

MYSQL_LOG_DIR=${WORK_DIR}/logs/mysql/
if [ ! -d ${MYSQL_LOG_DIR} ]; then
  mkdir -p ${MYSQL_LOG_DIR}
  if [ $? != 0 ]; then
    echo "Failed to create directory for mysql logs"
    exit 1
  else
    echo "Create directory ${MYSQL_LOG_DIR} for mysql logs successfully"
  fi
fi

# noted here, mysql image uses sbt user whose id is 999
# after successfully created the logs directory, need to chown to sbt in order
# to create log files successfully
# permission needed for MacOS
if [ $OS_NAME = "Darwin" ]; then
  chmod -R 777 ${MYSQL_LOG_DIR}
else
  chown 999:999 ${MYSQL_LOG_DIR}
fi

# before launching mysql service, clear mysql logs 
echo "clear logs generated in ${MYSQL_LOG_DIR}"
rm -rf ${MYSQL_LOG_DIR}/*

# To specify log file name, use '--general_log_file gen.log' as needed
# add more mysql logs
docker run --rm -it --name ustudy-dw --hostname dw-master -v ${WORK_DIR}/mysql/data:/var/lib/mysql \
    -v ${WORK_DIR}/mysql/schema/:/root/mysql/schema/ -v ${MYSQL_LOG_DIR}:/var/log/mysql/ \
    -v ${SCHEMA_DIR}/../scripts/master-mysqld.cnf:/etc/mysql/mysql.conf.d/mysqld.cnf \
    -p 13306:3306 -e MYSQL_ROOT_PASSWORD=mysql -d mysql:5.7 \
    --log-error=/var/log/mysql/mysqld.log  \
    --general_log=1 --general_log_file /var/log/mysql/gen.log \
    --slow_query_log=1 --slow_query_log_file /var/log/mysql/slow.log

if [ $? != 0 ];then
  echo "Failed to launch ustudy-dw container"
  exit 1
else
  echo "Launched ustudy-dw container successfully"
fi
if [ $OS_NAME = "Darwin" ]; then
  mkdir -p ${WORK_DIR}/nginx/frontend
  mkdir -p ${WORK_DIR}/logs/nginx
  chmod -R 777 ${WORK_DIR}/nginx
  chmod -R 777 ${WORK_DIR}/logs/nginx
fi
# start nginx as proxy for frontend services
docker run --rm -it --name nginx -p 443:443 -v ${WORK_DIR}/nginx/frontend/:/mnt/frontend/ \
    -v ${WORK_DIR}/logs/nginx/:/var/log/nginx/ -d nginx-ustudy:1.12
if [ $? != 0 ]; then
  echo "Failed to launch nginx container"
  docker stop ustudy-dw
  exit 1
else
  echo "Launched nginx container successfully"
fi

# set container timezone to Asia/Shanghai
#docker exec -u root ustudy-dw /bin/sh -c 'echo "Asia/Shanghai" > /etc/timezone; dpkg-reconfigure -f noninteractive tzdata'
#if [ $? != 0 ];then
#  echo "Failed to set container timezone to Asia/Shanghai"
#else
#  echo "Set timezone to Asia/Shanghai"
#fi

