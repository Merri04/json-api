package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class ApiKeyController {

    // Endepunkt for å generere og sende API-nøkkel
    @GetMapping(value = "/generate-api-key", produces = "application/json")
    public ResponseEntity<?> generateApiKey() {
        // Generere en unik API-nøkkel (her bruker vi UUID)
        String apiKey = UUID.randomUUID().toString();

        // Her kan du lagre nøkkelen i en database hvis du ønsker å koble den til en bruker eller til annen informasjon.
        // For nå returnerer vi bare den genererte nøkkelen.

        return ResponseEntity.ok(new ApiKeyResponse(apiKey));
    }

    // Response-klassen for å returnere API-nøkkelen
    public static class ApiKeyResponse {
        private String apiKey;

        public ApiKeyResponse(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }
}
