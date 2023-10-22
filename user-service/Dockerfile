FROM gradle:7.4-jdk17-alpine AS build
WORKDIR /app
COPY / /app
RUN gradle build

FROM amazoncorretto:17.0.0-alpine
COPY --from=build /app/build/libs/user-service-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
