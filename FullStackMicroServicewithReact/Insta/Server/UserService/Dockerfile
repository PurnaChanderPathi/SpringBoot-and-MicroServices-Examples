FROM maven:3.6.3-openjdk-11-slim as BUILDER
ARG VERSION=1.0
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src

RUN mvn clean package
COPY target/UserService-${VERSION}.jar target/application.jar

FROM openjdk:11.0.24-jre-slim
WORKDIR /app/

COPY --from=BUILDER /build/target/application.jar /app/
CMD java -jar /app/application.jar
