package org.example.kafkaApplication.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.kafkaApplication.Json.JsonSerializer;
import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class ProducerMain {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        try (org.apache.kafka.clients.producer.Producer<String, Task> producer = new KafkaProducer<>(properties)) {
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
                    ProducerRecord<String, Task> record = new ProducerRecord<>("task.events", task);
                    producer.send(record);
                } catch (Exception e) {
                    System.out.println("Problem in producer");
                }
            }
        }
    }
}
