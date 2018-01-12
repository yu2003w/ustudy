# Written by Jared on Jan 12, 2018.
# Try to replace localhost with real internal ip address and build dashboard, exam modules

# enable debug
#set -x

if [ $# != 2 ]; then
  echo "Please specify proper parameters"
  echo "Usage: build.sh [src dir] [internal host ip]"
  exit 1
fi

SRC_DIR=$1
InternalIP=$2

if [ ! -d ${SRC_DIR} ]; then
  echo "Source directory ${SRC_DIR} not existed"
  exit 1
fi

if [ -d ${SRC_DIR}/dashboard/ ]; then
  cd ${SRC_DIR}/dashboard/
  mvn clean
  find . -name "applicationContext.xml" | xargs sed -i "s/localhost/${InternalIP}/g"
  mvn clean package -DskipTests
fi

if [ -d ${SRC_DIR}/exam/ ]; then
  cd ${SRC_DIR}/exam/
  mvn clean
  find . -name "applicationContext.xml" | xargs sed -i "s/localhost/${InternalIP}/g"
  mvn clean package -DskipTests
fi

