FROM eclipse-temurin:21-jre-alpine
ADD ./build/libs/blocker-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]