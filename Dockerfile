FROM adoptopenjdk/openjdk16
COPY battle-service/build/libs/battle-service-1.0.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]