FROM eclipse-temurin:21-jre-alpine
ADD ./build/libs/store-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]