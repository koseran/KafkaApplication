package org.example.kafkaApplication.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.example.kafkaApplication.Producer.Task;


import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.example.kafkaApplication.Json.JsonDeserializer;

public class ConsumerPartition {
    public static void main(String[] args) {
        new ConsumerPartition().runConsumer();
    }

    public void runConsumer() {
        String bootstrapServers = "localhost:9092";
        String groupId = "group1";
        String topicName = "task.events";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id", groupId);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());


        Runnable consumer1Runnable = () -> {
            try (Consumer<String, Task> consumer = new KafkaConsumer<>(properties)) {
                List<TopicPartition> partitions = Arrays.asList(new TopicPartition(topicName, 0),
                        new TopicPartition(topicName, 1), new TopicPartition(topicName,2),new TopicPartition(topicName, 3),
                        new TopicPartition(topicName, 4), new TopicPartition(topicName,5));
                consumer.assign(partitions);


                while (true) {
                    ConsumerRecords<String, Task> records = consumer.poll(Duration.ofMillis(100));
                    records.forEach(record -> {
                        try {


                            Task receivedTask = record.value();
                            ObjectMapper obj = new ObjectMapper();
                            String jsonreceivedTask = obj.writeValueAsString(receivedTask);
                            System.out.println("Consumer 1-Message received: " + jsonreceivedTask);

                        } catch (Exception e) {
                            System.out.println("problem in ConsumerPartition");
                        }
                    });
                }
            }
        };

        // Create and run Consumer 2
        Runnable consumer2Runnable = () -> {
            try (Consumer<String, Task> consumer = new KafkaConsumer<>(properties)) {
                List<TopicPartition> partitions = Arrays.asList(new TopicPartition(topicName, 0),
                        new TopicPartition(topicName, 1), new TopicPartition(topicName,2),new TopicPartition(topicName, 3),
                        new TopicPartition(topicName, 4), new TopicPartition(topicName,5));
                consumer.assign(partitions);

                while (true) {
                    ConsumerRecords<String, Task> records = consumer.poll(Duration.ofMillis(100));
                    records.forEach(record -> {
                        try {


                            Task receivedTask1 = record.value();
                            ObjectMapper obj1 = new ObjectMapper();
                            String jsonreceivedTask1 = obj1.writeValueAsString(receivedTask1);
                            System.out.println("Consumer 2-Message received: " + jsonreceivedTask1);

                        } catch (Exception e) {
                            System.out.println("problem in ConsumerPartition");
                        }
                    });
                }
            }
        };

        // Start the consumer threads
        Thread consumer1Thread = new Thread(consumer1Runnable);
        Thread consumer2Thread = new Thread(consumer2Runnable);

        consumer1Thread.start();
        consumer2Thread.start();
/*
        try (Consumer<String, Task> consumer = new KafkaConsumer<>(properties)) {
        // Get partition information for the topic
        List<PartitionInfo> partitions = consumer.partitionsFor(topicName);

        // Consume messages from each partition
        for (PartitionInfo partition : partitions) {
            // Assign the consumer to a specific partition
            consumer.assign(Collections.singletonList(new TopicPartition(topicName, partition.partition())));

            // Seek to the beginning of the partition
            consumer.seekToBeginning(Collections.singletonList(new TopicPartition(topicName, partition.partition())));

            // Consume messages from the partition

            while (true) {
                ConsumerRecords<String, Task> records = consumer.poll(Duration.ofMillis(100));
                records.forEach(record -> {
                    try {


                        Task receivedTask = record.value();
                        ObjectMapper obj = new ObjectMapper();
                        String jsonreceivedTask = obj.writeValueAsString(receivedTask);
                        System.out.println(jsonreceivedTask);

                    } catch (Exception e) {
                        System.out.println("problem in consumermain");
                    }
                });
            }
        }
    }
}*/
    }}