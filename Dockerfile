FROM maven:3.6-jdk-8-alpine AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package

FROM openjdk:8-jre-alpine
EXPOSE 8080
EXPOSE 5005
COPY --from=builder /app/target/app.jar /

ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app.jar"]
