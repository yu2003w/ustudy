#!/bin/sh
if [ $# != 1 ]; then
  echo "Please specify WORK_DIR firstly"
  exit
fi
WORK_DIR=$1
echo "Using ${WORK_DIR} as working directory"
if [ ! -d ${WORK_DIR}/mysql/schema/ ]; then
  mkdir -p ${WORK_DIR}/mysql/schema/ 
  if [ $? != 0 ]; then
    echo "Failed to create schema directory" ${WORK_DIR}/mysql/schema/
    exit
  fi
  cp -Rf /home/repo/ustudy/schema/ ${WORK_DIR}/mysql/
  if [ $? != 0 ]; then
    echo "Failed to copy schema files into " ${WORK_DIR}/mysql/schema/
  else
    echo "Copy schema files into " ${WORK_DIR}/mysql/schema/ " successfully"
  fi
fi

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

# To specify log file name, use '--general_log_file gen.log' as needed
# add more mysql logs
docker run --rm -it --name web-mysql -v ${WORK_DIR}/mysql/data:/var/lib/mysql \
    -v ${WORK_DIR}/mysql/schema/:/root/mysql/schema/ -v ${MYSQL_LOG_DIR}:/var/lib/mysql/logs/ \
    -p 13306:3306 -e MYSQL_ROOT_PASSWORD=mysql -d mysql:5.7 \
    --log-error="logs/mysqld.log"  \
    --general_log=1 --general_log_file logs/gen.log \
    --slow_query_log=1 --slow_query_log_file logs/slow.log

# docker exec -u root web-mysql /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_infocenter'

