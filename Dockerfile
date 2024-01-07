FROM openjdk:21
COPY /target/delivery_service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE ${APP_PORT}
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]