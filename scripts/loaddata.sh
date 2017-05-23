#!/bin/sh
# Updated by Jared on May 23, 2017.

# load data for testing purpose

# load data for infocenter.student
docker exec web-mysql sh -c 'mysql -uroot -p"mysql" < /root/mysql/schema/load_stu'
if [ $? = 0 ]; then
  echo "load data for infocenter.student successful"
else
  echo "load data for infocenter.student failed"
fi

