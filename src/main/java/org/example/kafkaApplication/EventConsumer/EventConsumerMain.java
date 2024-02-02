package org.example.kafkaApplication.EventConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.kafkaApplication.Json.JsonDeserializer;
import org.example.kafkaApplication.Json.JsonSerializer;
import org.example.kafkaApplication.Producer.Task;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Stack;

public class EventConsumerMain {
    private final String bootstrapServers = "localhost:9092";
    private final String inputTopic = "task.events";
    private final String groupId = "my-consumer-group";

    public static void main(String[] args) {
        new EventConsumerMain().runEventConsumer();
    }

    public void runEventConsumer() {
        Properties consumerProperties = new Properties();
        Properties producerProperties = new Properties();
        consumerProperties.put("bootstrap.servers", bootstrapServers);
        consumerProperties.put("key.deserializer", StringDeserializer.class.getName());
        consumerProperties.put("value.deserializer", JsonDeserializer.class.getName());
        consumerProperties.put("group.id", groupId); // Ορίστε το group.id
        producerProperties.put("bootstrap.servers", bootstrapServers);
        producerProperties.put("key.serializer", StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        ObjectMapper obj = new ObjectMapper();
        try (Consumer<String, Task> consumer = new KafkaConsumer<>(consumerProperties);
             Producer<String, Task> producer = new KafkaProducer<>(producerProperties)) {

            consumer.subscribe(Collections.singletonList(inputTopic));

            while (true) {
                ConsumerRecords<String, Task> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Task> record : records) {
                    try {
                        Task receivedTask = record.value();
                        if (receivedTask.getSubject().equals("Subject0")) {
                            ProducerRecord<String, Task> producerRecord = new ProducerRecord<>("Subject0", receivedTask);
                            producer.send(producerRecord);
                            String jsonΤask = obj.writeValueAsString(receivedTask);
                            System.out.println(receivedTask.getSubject()+" Topic:"+jsonΤask);
                        } else if (receivedTask.getSubject().equals("Subject1")) {
                            ProducerRecord<String, Task> producerRecord = new ProducerRecord<>("Subject1",  receivedTask);
                            producer.send(producerRecord);
                            String jsonΤask = obj.writeValueAsString(receivedTask);
                            //System.out.println("Subject1 Topic:" + jsonΤask);
                        } else if (receivedTask.getSubject().equals("Subject2")) {
                            ProducerRecord<String, Task> producerRecord = new ProducerRecord<>("Subject2", receivedTask);
                            producer.send(producerRecord);
                            String jsonΤask = obj.writeValueAsString(receivedTask);
                            //System.out.println("Subject2 Topic:" + jsonΤask);
                        } else {
                            ProducerRecord<String, Task> producerRecord = new ProducerRecord<>("Subject3", receivedTask);
                            producer.send(producerRecord);
                            String jsonΤask = obj.writeValueAsString(receivedTask);
                            //System.out.println("Subject3 Topic:" + jsonΤask);
                        }


                    } catch (Exception e) {
                        System.out.println("problem in construction of topics");
                    }
                }
            }
        }
/*
    private Producer<String, Task> createProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        return new KafkaProducer<>(properties);
    }*/
/*
        private String extractSubjectFromJson (String json){
            // Replace with the logic to extract the "subject" field from JSON messages
            int startIndex = json.indexOf("\"subject\":\"") + 10;
            int endIndex = json.indexOf("\"", startIndex);
            return json.substring(startIndex, endIndex);
        }
*/
    }}