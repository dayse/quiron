#!/bin/bash

sed -i -e"s/Connector port=\"8080\"/Connector port=\"8080\" URIEncoding=\"UTF-8\"/g" /usr/share/tomcat/conf/server.xml

sed -i -e"s/username=\"tomcat\"/username=\""${MANAGER_USERNAME}"\"/g" /usr/share/tomcat/conf/tomcat-users.xml
sed -i -e"s/password=\"tomcat\"/password=\""${MANAGER_PASSWORD}"\"/g" /usr/share/tomcat/conf/tomcat-users.xml

/usr/share/tomcat/bin/startup.sh && tail -F /usr/share/tomcat/logs/catalina.out