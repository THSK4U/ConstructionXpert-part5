FROM openjdk:21
VOLUME /tmp
ADD target/*.jar project.jar
ENTRYPOINT ["java", "-jar", "project.jar"]
