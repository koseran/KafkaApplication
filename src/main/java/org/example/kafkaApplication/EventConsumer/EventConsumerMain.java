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
    private final String groupId = "my-consumer-group";

    public static void main(String[] args) {
        new EventConsumerMain().runEventConsumer();
    }

    public void runEventConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", bootstrapServers);
        consumerProperties.put("key.deserializer", StringDeserializer.class.getName());
        consumerProperties.put("value.deserializer", StringDeserializer.class.getName());
        consumerProperties.put("group.id", groupId); // Ορίστε το group.id

        try (Consumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);
             Producer<String, String> producer = createProducer()) {

            consumer.subscribe(Collections.singletonList(inputTopic));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    String subject = extractSubjectFromJson(record.value());
                    String outputTopic = "task.events." + subject;
                    producer.send(new ProducerRecord<>(outputTopic, record.value()));
                    System.out.println("Message forwarded to " + outputTopic + ": " + record.value());
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

    private String extractSubjectFromJson(String json) {
        // Replace with the logic to extract the "subject" field from JSON messages
        int startIndex = json.indexOf("\"subject\":\"") + 10;
        int endIndex = json.indexOf("\"", startIndex);
        return json.substring(startIndex, endIndex);
    }
}
