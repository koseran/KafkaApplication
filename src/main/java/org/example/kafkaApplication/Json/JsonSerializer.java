package org.example.kafkaApplication.Json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.example.kafkaApplication.Producer.Task;

import java.util.Map;

public class JsonSerializer implements Serializer<Task> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String s, Task task) {
        byte[] serializedBytes = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            serializedBytes = objectMapper.writeValueAsString(task).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serializedBytes;  
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Task data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
