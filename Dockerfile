FROM openjdk:21

LABEL author=stanleyvladimir2005@gmail.com

EXPOSE 8080

COPY "./build/libs/spring-reactor-0.0.1-SNAPSHOT.jar" "app.jar"

ENTRYPOINT ["java", "-jar", "/app.jar"]