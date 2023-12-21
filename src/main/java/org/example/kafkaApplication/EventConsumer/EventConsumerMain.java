package org.example.kafkaApplication.EventConsumer;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EventConsumerMain {
    private final String bootstrapServers = "localhost:9092";
    private final String inputTopic = "task.events";

    public static void main(String[] args) {
        new EventConsumerMain().runEventConsumer();
    }

    public void runEventConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", bootstrapServers);
        consumerProperties.put("key.deserializer", StringDeserializer.class.getName());
        consumerProperties.put("value.deserializer", StringDeserializer.class.getName());

        try (Consumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
             Producer<String, String> producer = createProducer()) {

            consumer.subscribe(Collections.singletonList(inputTopic));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    String subjectId = extractSubjectIdFromJson(record.value());
                    System.out.println(subjectId);
                    String outputTopic = "subject-specific-topic-" + subjectId;
                    producer.send(new ProducerRecord<>(outputTopic, record.value()));
                });
            }
        }
    }

    private Producer<String, String> createProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        return new KafkaProducer<>(properties);
    }

    private String extractSubjectIdFromJson(String json) {
        // Replace with the logic to extract the "subjectId" field from JSON messages
        // In this example, a simple substring assumption is made
        int startIndex = json.indexOf("\"subjectId\":\"") + 12;
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
