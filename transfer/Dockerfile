FROM eclipse-temurin:21-jre-alpine
ADD ./build/libs/transfer-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]