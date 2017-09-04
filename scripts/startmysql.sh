#!/bin/sh
# Initiated by Jared on May, 2017.
#
# Start mysql container to host data
#

if [ $# != 2 ]; then
  echo "Please specify WORK_DIR SCHEMA_DIR firstly"
  echo "Usage: startmysql.sh [work_dir] [schema_dir]"
  exit
fi
WORK_DIR=$1
SCHEMA_DIR=$2
echo "Using ${WORK_DIR} as working directory"
if [ ! -d ${WORK_DIR}/mysql/schema/ ]; then
  mkdir -p ${WORK_DIR}/mysql/schema/ 
  if [ $? != 0 ]; then
    echo "Failed to create schema directory" ${WORK_DIR}/mysql/schema/
    exit
  else
    echo "Created schema directory" ${WORK_DIR}/mysql/schema/
  fi
fi

cp -Rf ${SCHEMA_DIR} ${WORK_DIR}/mysql/
if [ $? != 0 ]; then
  echo "Failed to copy schema files into " ${WORK_DIR}/mysql/schema/
else
  echo "Copy schema files into " ${WORK_DIR}/mysql/schema/ " successfully"
fi

chown -R 999:999 ${WORK_DIR}/mysql/

MYSQL_LOG_DIR=${WORK_DIR}/logs/mysql/
if [ ! -d ${MYSQL_LOG_DIR} ]; then
  mkdir -p ${MYSQL_LOG_DIR}
  if [ $? != 0 ]; then
    echo "Failed to create directory for mysql logs"
    exit
  else
    echo "Create directory ${MYSQL_LOG_DIR} for mysql logs successfully"
  fi
fi

# noted here, mysql image uses sbt user whose id is 999
# after successfully created the logs directory, need to chown to sbt in order
# to create log files successfully
chown 999:999 ${MYSQL_LOG_DIR}

# before launching mysql service, clear mysql logs 
echo "clear logs generated in ${MYSQL_LOG_DIR}"
rm ${MYSQL_LOG_DIR}/*

# To specify log file name, use '--general_log_file gen.log' as needed
# add more mysql logs
docker run --rm -it --name ustudy-dw -v ${WORK_DIR}/mysql/data:/var/lib/mysql \
    -v ${WORK_DIR}/mysql/schema/:/root/mysql/schema/ -v ${MYSQL_LOG_DIR}:/var/log/mysql/ \
    -p 13306:3306 -e MYSQL_ROOT_PASSWORD=mysql -d mysql:5.7 \
    --log-error=/var/log/mysql/mysqld.log  \
    --general_log=1 --general_log_file /var/log/mysql/gen.log \
    --slow_query_log=1 --slow_query_log_file /var/log/mysql/slow.log

if [ $? != 0 ];then
  echo "Failed to launch ustudy-dw container"
else
  echo "Launched ustudy-dw container successfully"
fi

# set container timezone to Asia/Shanghai
#docker exec -u root ustudy-dw /bin/sh -c 'echo "Asia/Shanghai" > /etc/timezone; dpkg-reconfigure -f noninteractive tzdata'
#if [ $? != 0 ];then
#  echo "Failed to set container timezone to Asia/Shanghai"
#else
#  echo "Set timezone to Asia/Shanghai"
#fi

