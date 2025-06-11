FROM amazonlinux:latest

RUN yum update -y && \
    echo "[mongodb-org-7.0]" > /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "name=MongoDB Repository" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "baseurl=https://repo.mongodb.org/yum/redhat/9/mongodb-org/7.0/\$basearch/" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "gpgcheck=1" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "enabled=1" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    echo "gpgkey=https://www.mongodb.org/static/pgp/server-7.0.asc" >> /etc/yum.repos.d/mongodb-org-7.0.repo && \
    yum install -y mongodb-org && \
    yum clean all

EXPOSE 27017

ENTRYPOINT ["mongod", "--bind_ip_all"]