FROM ubuntu:precise
MAINTAINER Felipe Arruda Pontes <contato@arruda.blog.br>

ENV http_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128
ENV https_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128

RUN echo 'Acquire::http::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"
RUN echo 'Acquire::https::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"

# make sure the package repository is up to date
RUN apt-get update && apt-get -y install python-software-properties

RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update && apt-get -y upgrade

# automatically accept oracle license
RUN echo oracle-java6-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
# and install java 6 oracle jdk

RUN apt-get -y install oracle-java6-installer && apt-get clean
RUN update-alternatives --display java
RUN echo "JAVA_HOME=/usr/lib/jvm/java-6-oracle" >> /etc/environment

ENV JAVA_HOME /usr/lib/jvm/java-6-oracle
RUN ln -s /usr/lib/jvm/java-6-oracle/ /usr/lib/jvm/default-java



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

EXPOSE 8080

CMD /usr/share/tomcat/bin/startup.sh && tail -F /usr/share/tomcat/logs/catalina.out

