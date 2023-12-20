package org.example.kafkaApplication.Producer;
import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class Producer {
    public void runProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        try (org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 20; i++) {
                String taskId = "Task" + i;
                String studentId = "Student" + i;
                String subject = "Subject" + (i % 4); // 4 different work topics
                String dateOfSubmission = "2023-01-01";

                Task task = new Task(taskId, studentId, subject, dateOfSubmission);
                System.out.println(task.toJson());
                String jsonTask = task.toJson();
                System.out.println(jsonTask);
                ProducerRecord<String, String> record = new ProducerRecord<>("task.events", jsonTask);
                producer.send(record);
            }
        }
    }
}
