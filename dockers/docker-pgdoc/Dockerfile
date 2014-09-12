FROM ubuntu:14.04
MAINTAINER Felipe Arruda Pontes <contato@arruda.blog.br>

ENV http_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128
ENV https_proxy http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128
RUN echo 'Acquire::http::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"
RUN echo 'Acquire::https::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"

# UTF-8 locale
RUN locale-gen en_US.UTF-8 && \
    echo 'LANG="en_US.UTF-8"' > /etc/default/locale

# Update the Ubuntu
RUN apt-get update
RUN apt-get install -y -q postgresql-autodoc

# install graphviz to generate the png from the .dot
RUN apt-get install -y -q graphviz

ADD scripts /scripts
RUN chmod +x /scripts/start.sh

RUN mkdir /data


# expose data, where the png will be saved
VOLUME ["/data"]

WORKDIR /data

# Set the default command to run when starting the container
CMD ["/scripts/start.sh"]