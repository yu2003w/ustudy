#!/bin/sh
docker exec -u root web-mysql /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_infocenter'
