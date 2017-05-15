#!/bin/sh
docker run --rm -it --name web-mysql -v /home/jared/bench/mysql/data:/var/lib/mysql -p 13306:3306 -e MYSQL_ROOT_PASSWORD=mysql mysql:5.7
