#!/bin/sh
# Updated by Jared on May 23, 2017.

# load predefined data for applications
if [ $# != 1 ]; then
  echo "Usage: loaddata.sh [dev, prod]"
  echo "Please specify proper parameters"
  exit 1
fi

# load data for infocenter.student
docker exec ustudy-dw sh -c 'mysql -uroot -p"mysql" < /root/mysql/schema/load_data'
if [ $? = 0 ]; then
  echo "load production data successful"
else
  echo "load production data failed"
  exit 1
fi

if [ "$1"x = "dev"x ]; then
  docker exec ustudy-dw sh -c 'mysql -uroot -p"mysql" < /root/mysql/schema/load_sample_data'
  if [ $? = 0 ]; then
    echo "load sample data successful"
  else
    echo "load sample data failed"
    exit 2
  fi
fi

