FROM openjdk:8u242-jdk
MAINTAINER snake19870227
VOLUME ["/tmp"]
ARG profile
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN mkdir -p /boot-app
WORKDIR /boot-app
ADD ./target/StarTigerAdminUi.jar ./app.jar
EXPOSE 9999
ENTRYPOINT [\
    "java",\
    "-Dspring.profiles.active=${profile}",\
    "-Djava.security.egd=file:/dev/./urandom",\
    "-jar","app.jar"\
]