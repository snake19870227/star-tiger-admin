FROM logstash:7.6.2
MAINTAINER snake19870227
VOLUME ["/tmp"]
COPY ./logstash.conf /usr/share/logstash/pipeline/
USER root
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone