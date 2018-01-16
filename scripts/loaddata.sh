#!/bin/sh
# Updated by Jared on May 23, 2017.

# load predefined data for applications
if [ $# != 1 ]; then
  echo "Usage: loaddata.sh [dev, prod]"
  echo "Please specify proper parameters"
  exit 1
fi

# a little tricy here, if need to load data for development, move devconfig.data to config.data
if [ "$1"x = "dev"x ]; then
  docker exec ustudy-dw sh -c 'mv /root/mysql/schema/sample/configdev.csv /root/mysql/schema/sample/config.csv'
else
  docker exec ustudy-dw sh -c 'mv /root/mysql/schema/sample/configprod.csv /root/mysql/schema/sample/config.csv'
fi

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

