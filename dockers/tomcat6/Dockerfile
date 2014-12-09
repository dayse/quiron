FROM quiron_intjdk6
MAINTAINER Felipe Arruda Pontes <contato@arruda.blog.br>

ENV TOMCAT_VERSION 6.0.37
RUN mkdir -p /usr/share/tomcat


RUN wget --no-verbose -O /tmp/apache-tomcat-$TOMCAT_VERSION.tar.gz \
    http://archive.apache.org/dist/tomcat/tomcat-6/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz
# stop building if md5sum does not match
RUN echo "f90b100cf51ae0a444bef5acd7b6edb2  /tmp/apache-tomcat-$TOMCAT_VERSION.tar.gz" | \
    md5sum -c
# install tomcat in /usr/share/tomcat
RUN tar xzf /tmp/apache-tomcat-$TOMCAT_VERSION.tar.gz \
    --strip-components=1 -C /usr/share/tomcat
ENV CATALINA_HOME /usr/share/tomcat

VOLUME  ["/usr/share/tomcat/webapps", "/usr/share/tomcat/logs"]

ADD ./conf/tomcat-users.xml /usr/share/tomcat/conf/tomcat-users.xml
ADD ./lib/postgresql-8.3-607.jdbc3.jar /usr/share/tomcat/lib/postgresql-8.3-607.jdbc3.jar

ADD scripts /scripts
RUN chmod +x /scripts/start.sh

EXPOSE 8080

CMD /scripts/start.sh

