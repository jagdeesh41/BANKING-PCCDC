FROM openjdk:17-jdk-slim

LABEL version="v1" \
      maintainer="jagadeesh" \
      description="This is Cards ms"

RUN apt-get update && apt-get install -y curl

COPY target/credit-card-ms-1.0.0.jar credit-1.0.0.jar

ENTRYPOINT ["java", "-jar", "credit-1.0.0.jar"]
