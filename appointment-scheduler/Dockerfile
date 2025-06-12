FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean install -DskipTests

FROM amazonlinux:latest

RUN yum update -y && \
    yum install -y java-17-amazon-corretto-devel && \
    yum clean all

ENV JAVA_HOME /usr/lib/jvm/java-17-amazon-corretto
ENV PATH $JAVA_HOME/bin:$PATH

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
