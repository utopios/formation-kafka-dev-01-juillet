package com.example.demo_stream.service;


import com.example.demo_stream.entity.Transaction;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.time.Duration;

@Configuration
@EnableKafkaStreams
public class FraudDetectionStreamService {

    @Bean
    public KStream<String, Transaction> kStreamFraudDetection(StreamsBuilder builder) {
        KStream<String, Transaction> transactions = builder.stream("transactions");
        KTable<Windowed<String>, Long> fraudCounts = transactions
                .groupByKey()
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)).advanceBy(Duration.ofSeconds(10)))
                .count(Materialized.as("fraud-counts"));

        fraudCounts.toStream().filter((key, count) -> count >= 3)
                .map((key, value) -> new KeyValue<>(key.key(), "Fraud activity detected "+ value +  " in the last minute"))
                .to("fraud-alertes",Produced.with(Serdes.String(), Serdes.String()));

        return transactions;
    }
}
