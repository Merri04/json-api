package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Unified Bevisstudio API Proxy Controller
 * @Author Merri Sium Berhe
 * @Version 02.02.2025
 */
@RestController
@RequestMapping("/api/bevisstudio")
public class BevisStudioProxyController {

    private final WebClient webClient;

    public BevisStudioProxyController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping
    public Mono<ResponseEntity<?>> getUnifiedData() {
        // this is a proxy controller that fetches data from multiple endpoints and combines them into one response
        Mono<String> diplomas = webClient.get()
                .uri("/api/diplomas/my-diplomas")
                .headers(headers -> headers.setBasicAuth("S1212", "1212"))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(error -> Mono.just("Error fetching diplomas: " ));

        Mono<String> preauth = webClient.get()
                .uri("/api/bevis/send")
                .headers(headers -> headers.setBasicAuth("S1212", "1212"))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(20))
                .onErrorResume(error -> {
                    System.err.println("Error fetching preauth: " + error.getMessage());
                    return Mono.just("{\"error\":\"Failed to fetch preauth.\"}");
                });
        //this

        // this combines responses into one
        return Mono.zip(diplomas, preauth)
                .map(results -> {
                    Map<String, String> combinedResponse = new HashMap<>();
                    combinedResponse.put("diplomas", results.getT1());
                    combinedResponse.put("preauth", results.getT2());
                    return ResponseEntity.ok(combinedResponse);
                });
    }

    }




