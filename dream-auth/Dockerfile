#FROM java:8
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 7101
ENTRYPOINT ["java","-jar","/app.jar"]