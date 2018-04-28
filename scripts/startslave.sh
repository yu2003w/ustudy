# Written by Jared on March 2, 2018.
# Script to launch slave database for data replication and read-only analysis.

if [ $# != 2 ]; then
  echo "Please specify WORK_DIR and SCHEMA_DIR"
  echo "Usage: startslave.sh [work_dir]"
  exit 1
fi
WORK_DIR=$1
SCHEMA_DIR=$2

chown -R 999:999 ${WORK_DIR}/slave/

MYSQL_LOG_DIR=${WORK_DIR}/logs/slave/
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
chown 999:999 ${MYSQL_LOG_DIR}

# before launching mysql service, clear mysql logs
echo "clear logs generated in ${MYSQL_LOG_DIR}"
rm -rf ${MYSQL_LOG_DIR}/*

# To specify log file name, use '--general_log_file gen.log' as needed
# add more mysql logs
docker run --rm -it --name ustudy-dw-2 --hostname dw-slave -v ${WORK_DIR}/slave/data:/var/lib/mysql \
    -v ${WORK_DIR}/slave/schema/:/root/mysql/schema/ -v ${MYSQL_LOG_DIR}:/var/log/mysql/ \
    -v ${SCHEMA_DIR}/../scripts/slave-mysqld.cnf:/etc/mysql/mysql.conf.d/mysqld.cnf \
    -p 13307:3306 -e MYSQL_ROOT_PASSWORD=mysql -d mysql:5.7 \
    --log-error=/var/log/mysql/mysqld.log  \
    --general_log=1 --general_log_file /var/log/mysql/gen.log \
    --slow_query_log=1 --slow_query_log_file /var/log/mysql/slow.log

if [ $? != 0 ];then
  echo "Failed to launch dw-slave container"
  exit 1
else
  echo "Launched dw-slave container successfully"
fi
