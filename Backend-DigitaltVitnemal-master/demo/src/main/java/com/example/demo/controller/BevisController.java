package com.example.demo.controller;

import com.example.demo.client.BevisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Mono<String> sendBevisRequest() {
        return bevisClient.sendPostRequest();
    }
}