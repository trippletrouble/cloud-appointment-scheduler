FROM amazonlinux:latest
RUN yum update -y && \
    yum amazon-linux-extras install java-amazon-corretto17 -y && \
    clean all

ENV JAVA_HOME /usr/lib/jvm/java-17-amazon-corretto
ENV PATH $JAVA_HOME/bin:$PATH

RUN java -version
RUN javac -version

ARG JAR_FILE=target/*.jar

WORKDIR /app
