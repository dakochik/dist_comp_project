
FROM maven:3.6-jdk-11-slim AS builder
COPY . /opt/consumer
WORKDIR /opt/consumer
RUN mvn clean install

FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY --from=builder /opt/consumer/target/producer-*-SNAPSHOT.jar /opt/Producer.jar