package com.example.demo.controller;

import com.example.demo.client.BevisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 *

 @Author Elise Strand Bråtveit
 @Version 29.01.2025

 */

@RestController
@RequestMapping("/api/bevis")
public class BevisController {

    private final BevisClient bevisClient;

    @Autowired
    public BevisController(BevisClient bevisClient) {
        this.bevisClient = bevisClient;
    }
    @GetMapping("/send")
    public Mono<ResponseEntity<Map<String, String>>> sendBevisRequest() {
        Mono<String> urlMono = bevisClient.sendPostRequest();
        return urlMono
                .flatMap(url -> {
                    Map<String, String> responseMap = new HashMap<>();
                    responseMap.put("url", url);
                    return Mono.just(ResponseEntity.ok(responseMap));
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("url", "Feil ved forespørsel: " + ex.getResponseBodyAsString());
                    return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(errorMap));
                })
                .onErrorResume(Exception.class, ex -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("url", "Uventet feil: " + ex.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap));
                });
    }

}

