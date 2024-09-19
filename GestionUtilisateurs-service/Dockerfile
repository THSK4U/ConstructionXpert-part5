FROM openjdk:21
VOLUME /tmp
ADD target/*.jar auth.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]
