package com.example.demo.controller;

import com.example.demo.client.BevisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;
/*
 *
 @Author Elise Strand Bråtveit
 @Version 29.01.2025

 */
 /*
 * Denne klassen håndterer forespørsel til Bevis API.
 */

@RestController
@RequestMapping("/api/bevis")
public class BevisController {
@Autowired
    private BevisClient bevisClient;

//fjernet fødselsnummer fra getmapping
    @GetMapping(value = "/send", produces = "application/json")
    public Mono<ResponseEntity<Map<String, String>>> getAllDiplomaData() {
        //String fodselsnummer = requestBody.get("fodselsnummer");

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
                });}
    }


