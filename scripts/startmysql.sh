#!/bin/sh
rm -rf /home/jared/bench/mysql/schema/
cp -rf /home/repo/ustudy/schema/ /home/jared/bench/mysql/

docker run --rm -it --name web-mysql -v /home/jared/bench/mysql/data:/var/lib/mysql \
    -v /home/jared/bench/mysql/schema/:/root/mysql/schema/ -p 13306:3306 -e MYSQL_ROOT_PASSWORD=mysql mysql:5.7

# docker exec -u root web-mysql /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_infocenter'

