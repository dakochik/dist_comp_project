
FROM maven:3.6-jdk-11-slim AS builder

# Get Click Count job and compile it
COPY . /opt/consumer
WORKDIR /opt/consumer
RUN mvn clean install


###############################################################################
# Build Operations Playground Image
###############################################################################

FROM apache/flink:1.12.1-scala_2.11-java8

WORKDIR /opt/flink/bin

# Copy Click Count Job
COPY --from=builder /opt/consumer/target/consumer-*.jar /opt/Consumer.jar