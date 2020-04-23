FROM elasticsearch:7.6.2
MAINTAINER snake19870227
VOLUME ["/tmp"]
COPY ik /usr/share/elasticsearch/plugins/ik/
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone