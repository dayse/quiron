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

