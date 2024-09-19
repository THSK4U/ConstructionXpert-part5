FROM openjdk:21
VOLUME /tmp
ADD target/*.jar discovery.jar
ENTRYPOINT ["java", "-jar", "discovery.jar"]
