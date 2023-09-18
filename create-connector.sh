#!/bin/bash

## Wait for Kafka Connect to be ready
#while [ $(curl -s -o /dev/null -w %{http_code} http://127.0.0.1:8083/) -ne 200 ]; do
#    sleep 5
#done

# Register MongoDB connector
curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://127.0.0.1:8083/connectors/ -d '{
    "name": "mongodb-connector",
    "config": {
        "connector.class": "io.debezium.connector.mongodb.MongoDbConnector",
        "mongodb.hosts": "mongodb:27017",
        "mongodb.user": "root",
        "mongodb.password": "rootpassword",
        "mongodb.name": "testdb",
        "database.whitelist": "testdb",
        "tasks.max": "1",
        "key.converter": "org.apache.kafka.connect.json.JsonConverter",
        "value.converter": "org.apache.kafka.connect.json.JsonConverter"
    }
}'


curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/ -d '{
  "name": "mysql-connector",
  "config": {
      "connector.class": "io.debezium.connector.mysql.MySqlConnector",
      "database.hostname": "mysql",
      "database.port": "3306",
      "database.user": "user",
      "database.password": "password",
      "database.server.id": "1",
      "database.server.name": "mysql-server",
      "database.whitelist": "testdb",
      "database.history.kafka.bootstrap.servers": "kafka:9092",
      "database.history.kafka.topic": "schema-changes.testdb",
      "include.schema.changes": "true",
      "topic.prefix": "mysql-"
  }
}'