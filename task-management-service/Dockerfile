FROM openjdk:21
VOLUME /tmp
ADD target/*.jar tach.jar
ENTRYPOINT ["java", "-jar", "tach.jar"]
