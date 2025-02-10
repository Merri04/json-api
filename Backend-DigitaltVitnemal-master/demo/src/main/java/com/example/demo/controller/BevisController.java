package com.example.demo.controller;

import com.example.demo.client.BevisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 *

 @Author Elise Strand Bråtveit
 @Version 29.01.2025*/

@RestController
@RequestMapping("/api/bevis")
public class BevisController {

    private final BevisClient bevisClient;

    @Autowired
    public BevisController(BevisClient bevisClient) {
        this.bevisClient = bevisClient;
    }

    @GetMapping("/send")
    public Mono<ResponseEntity<String>> sendBevisRequest() {
        return bevisClient.sendPostRequest()
                .map(ResponseEntity::ok)
                .onErrorResume(WebClientResponseException.class, ex -> Mono.just(ResponseEntity.status(ex.getStatusCode())
                        .body("Feil ved forespørsel: " + ex.getResponseBodyAsString())))
                .onErrorResume(Exception.class, ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Uventet feil: " + ex.getMessage())));
    }
}
