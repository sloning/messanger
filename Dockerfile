FROM openjdk:11.0.7-jre-slim-buster
COPY build/libs/messenger.jar messenger.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/messenger.jar"]
