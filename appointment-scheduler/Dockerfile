FROM amazonlinux:latest
RUN yum update -y && \
    yum install -y shadow-utils procps && \
    yum clean all \

RUN amazon-linux-extras install java-amazon-corretto17 -y && \
    yum clean all
ENV JAVA_HOME /usr/lib/jvm/java-17-amazon-corretto
ENV PATH $JAVA_HOME/bin:$PATH

RUN echo "[mongodb-org-7.0]" > /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "name=MongoDB Repository" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "baseurl=https://repo.mongodb.org/yum/amazon/2/mongodb-org/7.0/x86_64/" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "gpgcheck=1" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "enabled=1" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "gpgkey=https://pgp.mongodb.com/server-7.0.asc" >> /etc/yum.repos.d/mongodb-org-7.0.repo
RUN yum install -y mongodb-org && \
    yum clean all
RUN mkdir -p /data/db_internal_app

ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar

COPY startup.sh /app/startup.sh
RUN chmod +x /app/startup.sh

EXPOSE 8080
ENTRYPOINT ["/app/startup.sh"]