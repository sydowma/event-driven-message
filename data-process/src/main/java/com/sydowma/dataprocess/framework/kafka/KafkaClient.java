package com.sydowma.dataprocess.framework.kafka;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class KafkaClient {

    private KafkaConsumer<String, byte[]> kafkaConsumer;


    private Set<String> currentTopics = new HashSet<>();
    private Map<String, Object> props;

    public KafkaClient() {
    }

    @PostConstruct
    public void init() {
        props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put("group.id", "test");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        this.kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Pattern.compile(".*"));
    }

    @Scheduled(fixedDelay = 6000) // Check for new topics every 6 seconds
    public void discoverTopics() {
        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsResult listTopics = adminClient.listTopics();
            Set<String> topics = listTopics.names().get();

            if (!currentTopics.containsAll(topics)) {
                kafkaConsumer.subscribe(topics);
                currentTopics = topics;
            }
        } catch (Exception e) {
            // Handle any exceptions that arise
            e.printStackTrace();
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        new Thread(this::poll).start();
    }

    public void poll() {
        while (true) {
            ConsumerRecords<String, byte[]> poll = this.kafkaConsumer.poll(Duration.ofMillis(50));
            for (ConsumerRecord<String, byte[]> record : poll) {
                System.out.println(new String(record.value()));
            }
        }

    }

//    public static void main(String[] args) throws InterruptedException {
//        KafkaClient kafkaClient = new KafkaClient();
//        kafkaClient.poll();
//        TimeUnit.MINUTES.sleep(10);
//    }
}
