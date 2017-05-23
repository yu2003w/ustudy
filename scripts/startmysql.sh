#!/bin/sh
if [ $# != 1 ]; then
  echo "Please specify WORK_DIR firstly"
  exit
fi
WORK_DIR=$1
echo "Working directory is " ${WORK_DIR}
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


docker run --rm -it --name web-mysql -v ${WORK_DIR}/mysql/data:/var/lib/mysql \
    -v ${WORK_DIR}/mysql/schema/:/root/mysql/schema/ -p 13306:3306 -e MYSQL_ROOT_PASSWORD=mysql mysql:5.7

# docker exec -u root web-mysql /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_infocenter'

