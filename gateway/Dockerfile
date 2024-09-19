FROM openjdk:21
VOLUME /tmp
ADD target/*.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
