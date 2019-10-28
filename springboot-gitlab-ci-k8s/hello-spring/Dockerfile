FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY  /target/demo-0.0.1-SNAPSHOT.jar app.jar
ENV PORT 5000
EXPOSE $PORT
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${PORT}","-jar","/app.jar"]