FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM tomcat:10.1.18-jdk17

#COPY target/CurrencyExchanger-1.0.war /usr/local/tomcat/webapps

COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/