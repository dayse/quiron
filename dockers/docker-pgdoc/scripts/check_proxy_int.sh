#!/bin/bash
if ping -c 1 www.google.com.br ; then
    echo "direct internet doing nothing"
else
    echo "proxy environment detected setting proxy"
    export http_proxy=http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128
    export https_proxy=http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128
    echo 'Acquire::http::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"
    echo 'Acquire::https::Proxy "http://felipe.pontes:qualquercoisa@proxy.corp.int.gov.br:3128";' >> "/etc/apt/apt.conf.d/01proxy"
fi