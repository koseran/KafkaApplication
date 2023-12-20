package org.example.kafkaApplication;


import org.example.kafkaApplication.EventConsumer.EventConsumer;
import org.example.kafkaApplication.Producer.Producer;
import org.example.kafkaApplication.Consumer.Consumer;
public class Main {
    public static void main(String[] args) {
        // Καλέστε τον παραγωγό
        Producer producer = new Producer();
        producer.runProducer();

        // Καλέστε τον πρώτο καταναλωτή
        Consumer consumer = new Consumer();
        consumer.runConsumer();

        // Καλέστε τον δεύτερο καταναλωτή
        EventConsumer eventConsumer = new EventConsumer();
        eventConsumer.eventConsumer();
        eventConsumer.toString();
    }
}