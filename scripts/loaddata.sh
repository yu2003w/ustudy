#!/bin/sh
# Updated by Jared on May 23, 2017.

# load data for testing purpose

# load data for infocenter.student
docker exec ustudy-dw sh -c 'mysql -uroot -p"mysql" < /root/mysql/schema/load_data'
if [ $? = 0 ]; then
  echo "load data for testing successful"
else
  echo "load data for testing failed"
fi

