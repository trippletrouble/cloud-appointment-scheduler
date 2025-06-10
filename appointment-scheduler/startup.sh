#!/bin/bash
echo "Attempting to start MongoDB..."
mkdir -p /var/log/mongodb
mongod --dbpath /data/db_internal_app --logpath /var/log/mongodb/mongod.log --bind_ip_all --fork
echo "Waiting for MongoDB to initialize (15 sec)"
sleep 15

if ! pgrep mongod > /dev/null; then
    echo "MongoDB process not found."
    if [ -f /var/log/mongodb/mongod.log ]; then
        echo "--- MongoDB Log ---"
        cat /var/log/mongodb/mongod.log
        echo "--- MongoDB Log End ---"
    fi
    exit 1
else
    echo "MongoDB process found and running."
fi

echo "MongoDB startup sequence finished. Starting Spring app"
java -jar /app/app.jar
