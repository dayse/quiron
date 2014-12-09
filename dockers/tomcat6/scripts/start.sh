#!/bin/bash

sed -i -e"s/username=\"tomcat\"/username=\""${MANAGER_USERNAME}"\"/g" /usr/share/tomcat/conf/tomcat-users.xml
sed -i -e"s/password=\"tomcat\"/password=\""${MANAGER_PASSWORD}"\"/g" /usr/share/tomcat/conf/tomcat-users.xml

/usr/share/tomcat/bin/startup.sh && tail -F /usr/share/tomcat/logs/catalina.out