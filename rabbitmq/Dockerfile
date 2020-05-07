FROM rabbitmq:3
MAINTAINER snake19870227
VOLUME ["/tmp"]
USER root
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone \
    && rabbitmq-plugins enable rabbitmq_management rabbitmq_web_stomp_examples