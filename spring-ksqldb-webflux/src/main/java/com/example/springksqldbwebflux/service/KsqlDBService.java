package com.example.springksqldbwebflux.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Service
public class KsqlDBService {
    private final WebClient webClient;

    public KsqlDBService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<String> executeKsqlQuery(String query) {
        String body = "{\"ksql\": \""+query +"\", \"streamProperties\": {}}";
        return webClient.post().uri("/query-stream")
                .header("Content-type", "application/vnd.ksql.v1+json; charset=utf-8")
                .body(BodyInserters.fromValue(body)).retrieve().bodyToFlux(String.class);
    }
}
