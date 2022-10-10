FROM openjdk:11
EXPOSE 8080
COPY "./build/libs/spring-reactor-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java", "-jar", "/app.jar"]