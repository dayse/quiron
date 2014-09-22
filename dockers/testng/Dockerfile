FROM quiron_intjdk6
MAINTAINER Felipe Arruda Pontes <contato@arruda.blog.br>

RUN mkdir /testng_libs
ADD ./lib/postgresql-8.3-607.jdbc3.jar /testng_libs/postgresql-8.3-607.jdbc3.jar

RUN mkdir /project


VOLUME  ["/project"]

WORKDIR /project

CMD java -cp "/testng_libs/*:build/classes/:WebContent/WEB-INF/lib/*"  org.testng.TestNG /project/conf/test/testng.xml


