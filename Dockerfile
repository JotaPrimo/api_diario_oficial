FROM openjdk:21
LABEL authors="jotasantos"

ARG JAR_FILE=target/*.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

# Expõe a porta na qual o Spring Boot vai rodar (8080 por padrão)
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]