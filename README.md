Initiated by Jared on May 9, 2017.

Code repository for services of ustudy.
Each service should be deployed and run as separate services in tomcat container.

Following modules are included:
1, Dashboard
   Administration console, core module
2, Information Center
   Informaction center for users.

Procedure to setup development environment:
1, git clone repository
2, build war packages
   go to directory dashboard, infocen
   mvn clean package -DskipTests
   If need to use eclipse for development/testing,
   mvn eclipse:clean eclipse:eclipse
3, start mysql as docker container
   ./startmysql.sh ~/bench /home/repo/ustudy/schema
   ./installdb.sh
   ./loaddata.sh
4, start tomcat to hold war packages
   ./startup.sh ~/bench /home/repo
5, mysql/tomcat logs is under ~/bench/logs/


