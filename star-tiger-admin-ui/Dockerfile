FROM openjdk:8u242-jdk
MAINTAINER snake19870227
VOLUME ["/tmp"]
EXPOSE 9999
ARG ACTIVE_PROFILE
COPY target/StarTigerAdminUi.jar /opt/
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone \
    && echo "#!/bin/bash" > /opt/run.sh \
    && echo "java -Dspring.profiles.active=${ACTIVE_PROFILE} -Djava.security.egd=file:/dev/./urandom -jar StarTigerAdminUi.jar" >> /opt/run.sh \
    && chmod u+x /opt/run.sh
WORKDIR /opt
ENTRYPOINT ./run.sh