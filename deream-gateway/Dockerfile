FROM openjdk:8u212-jre
COPY ./target/deream-gateway-1.0-SNAPSHOT.jar /gateway.jar
EXPOSE 7301
ENTRYPOINT ["java","-jar","/gateway.jar"]