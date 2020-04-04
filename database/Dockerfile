FROM mysql:5.7.29
MAINTAINER snake19870227
VOLUME ["/tmp"]
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt-get update
RUN apt-get install iputils-ping