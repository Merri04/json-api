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

import java.util.HashMap;
import java.util.Map;

/**
 *

 @Author Elise Strand Bråtveit
 @Version 29.01.2025*/

/*@RestController
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
}*/
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
        // Kall på sendPostRequest som returnerer et Mono<String>
        Mono<String> urlMono = bevisClient.sendPostRequest();

        // Bruk flatMap for å håndtere asynkrone Mono-verdier
        return urlMono
                .flatMap(url -> {
                    // Bytt ut den harde URL-en med den mottatte URL-en fra urlMono
                    Map<String, String> responseMap = new HashMap<>();
                    responseMap.put("url", url); // Sett den faktiske URL-en
                    return Mono.just(ResponseEntity.ok(responseMap));
                })
                .onErrorResume(WebClientResponseException.class, ex -> {
                    // Håndter feil fra API-kall
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("url", "Feil ved forespørsel: " + ex.getResponseBodyAsString());
                    return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(errorMap));
                })
                .onErrorResume(Exception.class, ex -> {
                    // Håndter generelle feil
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("url", "Uventet feil: " + ex.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap));
                });
    }

    /*@GetMapping("/send")
    public Mono<ResponseEntity<Map<String, String>>> sendBevisRequest() {

        return bevisClient.sendPostRequest()
                .map(response -> {
                    // Bytt dette med riktig URL
                    Map<String, String> responseMap = new HashMap<>();
                    responseMap.put("url", "https://example.com");
                    return ResponseEntity.ok(responseMap);
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
    }*/

}

