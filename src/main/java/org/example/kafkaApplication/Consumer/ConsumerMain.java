package org.example.kafkaApplication.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.example.kafkaApplication.Producer.Task;


import java.time.Duration;
import java.util.*;

import org.example.kafkaApplication.Json.JsonDeserializer;

public class ConsumerMain {

    public static void main(String[] args) {
        System.out.println("Dwse groupId: ");
        Scanner input = new Scanner(System.in);
        String groupId = input.nextLine();
        new ConsumerMain().runConsumer(groupId);
    }

    public void runConsumer(String groupId) {
        String bootstrapServers = "localhost:9092";
        //String groupId = UUID.randomUUID().toString();
        System.out.println(groupId);
        String topicName = "task.events";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("group.id", groupId);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());


        ObjectMapper obj = new ObjectMapper();

            try (Consumer<String, Task> consumer = new KafkaConsumer<>(properties)) {

                consumer.subscribe(Collections.singletonList(topicName));

                while (true) {
                    ConsumerRecords<String, Task> records = consumer.poll(Duration.ofMillis(100));
                    records.forEach(record -> {
                        try {


                            Task receivedTask = record.value();
                            String jsonreceivedTask = obj.writeValueAsString(receivedTask);
                            System.out.println("Message received: " + jsonreceivedTask);

                        } catch (Exception e) {
                            System.out.println("problem in consumermain");
                        }
                    });
                }
            }
        }

    }