package com.example.demo_stream.service;


import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Duration;

@Configuration
@EnableKafkaStreams
public class LogStreamProcessService {

    @Bean
    public KStream<String, Long> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> kStream = streamsBuilder.stream("log-topic");
        KStream<String, String> filterStream = kStream.filter((key, value) -> {
            return value.contains("suspect");
        });
        KTable<Windowed<String>, Long> aggregatedStream = filterStream.groupByKey().windowedBy(TimeWindows.of(Duration.ofMinutes(1))).count();

        KStream<String, Long> finalStream = aggregatedStream.toStream().map((windows, count) -> KeyValue.pair(windows.key(), count));
        finalStream.to("alert-topic");
        return  finalStream;
    }
}
