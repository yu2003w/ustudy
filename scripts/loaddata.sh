#!/bin/sh
docker exec web-mysql sh -c 'mysql -uroot -p"mysql" < /root/mysql/schema/load_stu'
