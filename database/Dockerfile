FROM mysql:5.7.29

MAINTAINER snake19870227

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./sql.sql /docker-entrypoint-initdb.d
