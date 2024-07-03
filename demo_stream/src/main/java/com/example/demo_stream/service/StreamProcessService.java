package com.example.demo_stream.service;


import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@Configuration
@EnableKafkaStreams
public class StreamProcessService {

//    @Bean
//    public KStream<String, String> kStream(StreamsBuilder streamsBuilder) {
//        KStream<String, String> kStream = streamsBuilder.stream("demo-stream-topic");
//        KStream<String, String> aggregatedStream = kStream.mapValues(value -> " " + value +" ").groupByKey()
//                .reduce((oldElement, newElement) -> oldElement + newElement).toStream();
//
//        aggregatedStream.to("demo-output-stream-topic");
//        return aggregatedStream;
//    }
}
