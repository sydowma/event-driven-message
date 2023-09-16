package com.sydowma.dataprocess.framework.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class KafkaClient {

    private KafkaConsumer<String, byte[]> kafkaConsumer;

    public KafkaClient() {
        Map<String, Object> pro = new HashMap<>();
        pro.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        pro.put("group.id", "test");
        pro.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        pro.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        this.kafkaConsumer = new KafkaConsumer<>(pro);
        kafkaConsumer.subscribe(Pattern.compile(".*"));
    }

    public void poll() {
        while (true) {
            ConsumerRecords<String, byte[]> poll = this.kafkaConsumer.poll(Duration.ofMillis(50));
            for (ConsumerRecord<String, byte[]> record : poll) {
                System.out.println(new String(record.value()));
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        KafkaClient kafkaClient = new KafkaClient();
        kafkaClient.poll();
        TimeUnit.MINUTES.sleep(10);
    }
}
