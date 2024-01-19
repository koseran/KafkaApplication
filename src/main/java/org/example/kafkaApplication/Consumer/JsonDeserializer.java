package org.example.kafkaApplication.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.kafkaApplication.Producer.Task;

import java.nio.ByteBuffer;
import java.util.Map;

public class JsonDeserializer implements Deserializer<Task> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Task deserialize(String s, byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        try {
            return objectMapper.readValue(bytes, Task.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing Task from JSON", e);
        }
    }
    @Override
    public Task deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public Task deserialize(String topic, Headers headers, ByteBuffer data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
