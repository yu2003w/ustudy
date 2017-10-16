#!/bin/sh
# Initiated by Jared on May 20, 2017.

# Instatll databases for each services

# databases for dashboard
docker exec -u root ustudy-dw /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_dashboard'
if [ $? = 0 ]; then
  echo "Install database for service dashboard successfully"
else
  echo "Install database for service dashboard failed"
  exit 1
fi

# databases for infocenter
docker exec -u root ustudy-dw /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_infocenter'
if [ $? = 0 ]; then
  echo "Install database for service infocenter successfully"
else
  echo "Install database for service infocenter failed"
  exit 1
fi

# databases for examination center
docker exec -u root ustudy-dw /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_exam'
if [ $? = 0 ]; then
  echo "Install database for service examination center successfully"
else
  echo "Install database for service examination center failed"
  exit 1
fi

# databases for score analytics
docker exec -u root ustudy-dw /bin/sh -c 'mysql -u root -p"mysql" < /root/mysql/schema/install_score'
if [ $? = 0 ]; then
  echo "Install database for score analytics successfully"
else
  echo "Install database for score analytics failed"
  exit 1
fi

