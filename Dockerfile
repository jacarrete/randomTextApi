FROM openjdk:8-jre-alpine
LABEL maintainer="Javier Carretero<javi_1986@hotmail.com>"
COPY target/betvictor*.jar /usr/src/betvictor/betvictor.jar
WORKDIR /usr/src/betvictor

RUN apk update \
    && apk add curl

HEALTHCHECK --interval=30s --timeout=10s --start-period=5m \
 CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080 9090

CMD java -jar betvictor.jar