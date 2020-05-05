FROM amazoncorretto:8
MAINTAINER snake19870227
VOLUME ["/tmp"]
EXPOSE 9999
ARG ACTIVE_PROFILE
COPY target/STigerAdminWeb.jar /opt/
COPY filebeat/filebeat-7.6.2-x86_64.rpm /opt/
COPY filebeat/filebeat.yml /opt/
USER root
WORKDIR /opt
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo Asia/Shanghai > /etc/timezone \
    && echo "#!/bin/bash" > /opt/run.sh \
    && echo "java -Dspring.profiles.active=${ACTIVE_PROFILE} -Djava.security.egd=file:/dev/./urandom -jar STigerAdminWeb.jar" >> /opt/run.sh \
    && chmod u+x /opt/run.sh \
#    && curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-7.6.2-amd64.deb \
    && rpm -vi filebeat-7.6.2-x86_64.rpm
ENTRYPOINT ./run.sh
#ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-Djava.security.egd=file:/dev/./urandom", "-jar", "STigerAdminWeb.jar"]
#CMD filebeat -e -c /opt/filebeat.yml