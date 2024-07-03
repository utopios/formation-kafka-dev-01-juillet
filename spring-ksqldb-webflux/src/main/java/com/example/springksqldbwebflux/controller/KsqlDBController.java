package com.example.springksqldbwebflux.controller;

import com.example.springksqldbwebflux.service.KsqlDBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class KsqlDBController {
    private final KsqlDBService ksqlDBService;

    public KsqlDBController(KsqlDBService ksqlDBService) {
        this.ksqlDBService = ksqlDBService;
    }
    @GetMapping(value = "/query-stream", produces = "application/stream+json")
    public Flux<String> queryKsqlDb(@RequestParam String query) {
        return ksqlDBService.executeKsqlQuery(query);
    }

}
