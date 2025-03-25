package com.example.demo.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/igrant")
    public ResponseEntity<String> proxyToIgrant(@RequestBody Map<String, Object> requestBody) {
        String igrantUrl = "https://prototype-lomino-issuer.azurewebsites.net/api/preauthorize";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    igrantUrl, HttpMethod.POST, requestEntity, String.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Feil ved videresending til iGrant.");
        }
    }
}

