FROM openjdk:17-alpine
COPY build/libs/messenger.jar messenger.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/messenger.jar"]
