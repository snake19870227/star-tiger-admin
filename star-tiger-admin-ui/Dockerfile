FROM openjdk:8u242-jdk
MAINTAINER snake19870227
VOLUME ["/tmp"]
ARG ACTIVE_PROFILE
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /boot-app
WORKDIR /boot-app
ADD ./target/StarTigerAdminUi.jar ./app.jar
EXPOSE 9999
RUN echo "java -Dspring.profiles.active=${ACTIVE_PROFILE} -Djava.security.egd=file:/dev/./urandom -jar app.jar" > run.sh
RUN chmod u+x run.sh
CMD ./run.sh