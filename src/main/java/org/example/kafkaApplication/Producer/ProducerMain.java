package org.example.kafkaApplication.Producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.*;
import org.example.kafkaApplication.Json.JsonSerializer;

import java.util.Properties;

public class ProducerMain {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());


        try (Producer<String, Task> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 20; i++) {
                String taskId = "Task" + i;
                String studentId = "Student" + i;
                String subject = "Subject" + (i % 4); // 4 different work topics
                String dateOfSubmission = "2023-01-01";
                try {
                    Task task = new Task(taskId, studentId, subject, dateOfSubmission);
                    ObjectMapper obj = new ObjectMapper();
                    String jsonΤask = obj.writeValueAsString(task);
                    System.out.println(jsonΤask);
                    //ProducerRecord<String, Task> record = new ProducerRecord<>("task.events", task);
                    ProducerRecord<String, Task> record = new ProducerRecord<>("task.events",i % 6, null,task);
                    producer.send(record, new MyProducerCallback());
                } catch (Exception e) {
                    System.out.println("Problem in producer");
                }
            }
        }
    }

    private static class MyProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            if (exception == null) {
                // Message sent successfully
                System.out.println("Message sent to topic " + metadata.topic() +
                        ", partition " + metadata.partition() +
                        ", offset " + metadata.offset());
            } else {
                // An error occurred
                System.err.println("Error sending message: " + exception.getMessage());
            }
        }
    }
}
