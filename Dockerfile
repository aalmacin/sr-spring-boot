FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG jar
ARG username
ARG password
ARG dbusername
ARG dbpassword

ADD ${jar} app.jar

ENV SPACED_REPETITION_USERNAME ${username}
ENV SPACED_REPETITION_PASSWORD ${password}
ENV SPACED_REPETITION_DB_USERNAME ${dbusername}
ENV SPACED_REPETITION_DB_PASSWORD ${dbpassword}

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
