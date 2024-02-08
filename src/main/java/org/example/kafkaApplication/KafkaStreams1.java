package org.example.kafkaApplication;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.*;

import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

import org.example.kafkaApplication.Producer.Task;
import org.apache.kafka.streams.StreamsBuilder;
import org.example.kafkaApplication.Json.JsonSerializer;
import org.example.kafkaApplication.Json.JsonDeserializer;


import java.util.Properties;

public class KafkaStreams1 {
    static Serializer<Task> jsonSerializer = new JsonSerializer();
    static Deserializer<Task> jsonDeserializer = new JsonDeserializer();
    static Serde<Task> jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "task-events");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, jsonSerde.getClass());



        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String,Task> firstStream = streamsBuilder.stream("task.events", Consumed.with(Serdes.String(),jsonSerde));
        firstStream.to("newTaskevents",Produced.with(Serdes.String(),jsonSerde));
        firstStream.foreach((k,v) -> System.out.println("Key= "+k+", Value= "+v.getSubject()));
        final Topology topology = streamsBuilder.build();
        System.out.println(topology.describe());
        final KafkaStreams streams = new KafkaStreams(topology, properties);
        //


        try{
             streams.start();

        } catch (Exception e){
            System.out.println("Problem in streams.start");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            streams.close();
        }));


    }
}

