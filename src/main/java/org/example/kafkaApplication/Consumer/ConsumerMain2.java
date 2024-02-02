package org.example.kafkaApplication.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.example.kafkaApplication.Json.JsonDeserializer;
import org.example.kafkaApplication.Producer.Task;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ConsumerMain2 {

    public void runConsumer() {
        String bootstrapServers = "localhost:9092";
        String groupId = "group2";
        String topic = "taskPartition";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id",groupId);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        System.out.println("Inside concumer 2");
        try (Consumer<String, Task> consumer = new KafkaConsumer<>(properties)) {
            //consumer.subscribe(Collections.singletonList(topic));
            List<TopicPartition> partitions = Arrays.asList(new TopicPartition(topic, 0));//,new TopicPartition(topic, 4),new TopicPartition(topic, 5));
            consumer.assign(partitions);
            while (true) {
                ConsumerRecords<String, Task> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    try {


                        Task receivedTask = record.value();
                        ObjectMapper obj = new ObjectMapper();
                        String jsonreceivedTask = obj.writeValueAsString(receivedTask);
                        System.out.println("Message received from consumer 2: " + jsonreceivedTask);

                    } catch (Exception e) {
                        System.out.println("problem in consumermain");
                    }
                });
            }
        }
    }
}
