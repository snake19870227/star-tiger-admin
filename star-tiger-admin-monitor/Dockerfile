FROM amazoncorretto:8
MAINTAINER snake19870227
VOLUME ["/tmp"]
EXPOSE 9988
ARG ACTIVE_PROFILE
COPY target/STigerAdminMonitor.jar /opt/
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone \
    && echo "#!/bin/bash" > /opt/run.sh \
    && echo "java -Dspring.profiles.active=${ACTIVE_PROFILE} -Djava.security.egd=file:/dev/./urandom -jar STigerAdminMonitor.jar" >> /opt/run.sh \
    && chmod u+x /opt/run.sh
WORKDIR /opt
ENTRYPOINT ./run.sh