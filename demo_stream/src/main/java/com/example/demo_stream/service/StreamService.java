package com.example.demo_stream.service;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.stereotype.Service;

@Service
public class StreamService {
    StreamsBuilder streamsBuilder;
    KafkaStreams kafkaStreams;
    public StreamService() {
        streamsBuilder = new StreamsBuilder();
        streamsBuilder.stream("demo-topic");
        //kafkaStreams = new KafkaStreams(streamsBuilder.build(), null);
    }
}
