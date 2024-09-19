FROM openjdk:21
VOLUME /tmp
ADD target/*.jar res.jar
ENTRYPOINT ["java", "-jar", "res.jar"]
