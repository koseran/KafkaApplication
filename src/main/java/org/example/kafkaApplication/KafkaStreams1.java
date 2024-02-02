package org.example.kafkaApplication;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;

import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.example.kafkaApplication.Json.JsonDeserializer;
import org.example.kafkaApplication.Json.JsonSerializer;
import org.example.kafkaApplication.Producer.ProducerMain;
import org.example.kafkaApplication.Producer.Task;
import org.apache.kafka.streams.StreamsBuilder;

import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class KafkaStreams1 {
   // private static final Logger logger = Logger.getLogger(String.valueOf(ProducerMain.class));
   static String inputTopic = "task.events";


    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "task-events");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String());
        /*
        properties.put("value.serializer", "org.example.kafkaApplication.Json.JsonSerializer");
        properties.put("key.deserializer", "org.example.kafkaApplication.Json.JsonDeserializer");
        properties.put("value.deserializer", "org.example.kafkaApplication.Json.JsonDeserializer");*/

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String,String> firstStream = streamsBuilder.stream(inputTopic, Consumed.with(Serdes.String(),Serdes.String()));
        KStream<String, String> inputTopic = streamsBuilder.stream("task.events");
        firstStream.peek((k,v)->System.out.println("Key= "+ k +"Value= "+ v));


        /*Topology topology = streamsBuilder.build();
        KafkaStreams streams = new KafkaStreams(topology, properties);
        //logger.info("Starting stream");
        try{
        streams.start();
        } catch (Exception e){
            System.out.println("Problem in streams.start");
        }
        try{
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
           streams.close();
        }));}catch (Exception e){
            System.out.println("Problem in close");
        }*/


}}

