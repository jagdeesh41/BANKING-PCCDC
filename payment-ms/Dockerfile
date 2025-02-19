FROM openjdk:17-jdk-slim

LABEL version="v1" \
      maintainer="jagadeesh" \
      description="This is Payment ms"

RUN apt-get update && apt-get install -y curl

COPY target/payment-ms-1.0.0.jar payment-1.0.0.jar

ENTRYPOINT ["java", "-jar", "payment-1.0.0.jar"]
