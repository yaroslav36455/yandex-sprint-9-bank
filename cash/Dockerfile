FROM eclipse-temurin:21-jre-alpine
ADD ./build/libs/exchange-generator-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]