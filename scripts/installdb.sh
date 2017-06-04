#!/bin/sh
# Initiated by Jared on May 20, 2017.

# Instatll databases for each services

# databases for infocenter
docker exec -u root web-mysql /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_infocenter'
if [ $? = 0 ]; then
  echo "Install database for service infocenter successfully"
else
  echo "Install database for service infocenter failed"
fi

# databases for infocenter
docker exec -u root web-mysql /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_admin'
if [ $? = 0 ]; then
  echo "Install database for service admin successfully"
else
  echo "Install database for service admin failed"
fi

