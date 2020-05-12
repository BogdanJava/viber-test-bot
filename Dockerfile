FROM maven:3.6-jdk-8-alpine AS builder

EXPOSE 8080

WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn -e -B package

FROM openjdk:8-jre-alpine
COPY --from=builder /app/target/app.jar /

ENTRYPOINT ["java", "-jar", "/app.jar"]
