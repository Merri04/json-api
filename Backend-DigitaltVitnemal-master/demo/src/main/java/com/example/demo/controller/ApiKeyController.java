package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiKeyController {

    // Hardkodet API-nøkkel
    private static final String API_KEY = "d3b07384d113edec49eaa6238ad5ff00";  // Eksempel på API-nøkkel

    // Endepunkt for å hente den hardkodede API-nøkkelen
    @GetMapping(value = "/generate-api-key", produces = "application/json")
    public ResponseEntity<?> generateApiKey() {
        return ResponseEntity.ok(new ApiKeyResponse(API_KEY));
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
