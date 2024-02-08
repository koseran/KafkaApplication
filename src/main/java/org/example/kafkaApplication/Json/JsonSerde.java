package org.example.kafkaApplication.Json;

import org.apache.kafka.common.serialization.Serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.example.kafkaApplication.Json.JsonDeserializer;
import org.example.kafkaApplication.Json.JsonSerializer;

import java.util.Map;

public class JsonSerde<Task> implements Serde<Task> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Serializer<Task> serializer() {
        return (Serializer<Task>) new JsonSerializer();
    }

    @Override
    public Deserializer<Task> deserializer() {
        return (Deserializer<Task>) new JsonDeserializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration needed
    }

    @Override
    public void close() {
        // No resources to close
    }
}



