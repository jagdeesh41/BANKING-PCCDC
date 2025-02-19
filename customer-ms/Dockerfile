FROM openjdk:17-jdk-slim

LABEL version="v1" \
      maintainer="jagadeesh" \
      description="This is Accounts ms"

RUN apt-get update && apt-get install -y curl

COPY target/accounts-ms-1.0.0.jar accounts-1.0.0.jar

ENTRYPOINT ["java", "-jar", "accounts-1.0.0.jar"]

