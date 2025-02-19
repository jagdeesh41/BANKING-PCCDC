FROM openjdk:17-jdk-slim

LABEL version="v1" \
      maintainer="jagadeesh" \
      description="This is Eureka ms"

RUN apt-get update && apt-get install -y curl

COPY target/eureka-server-1.0.0.jar eureka-1.0.0.jar

ENTRYPOINT ["java", "-jar", "eureka-1.0.0.jar"]
