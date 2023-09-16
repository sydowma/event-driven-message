 /opt/bitnami/kafka/bin/kafka-topics.sh --bootstrap-server kafka:9092 --list

/opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic your_topic_name --from-beginning


/opt/bitnami/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic mysql-.testdb.sadd --from-beginning
