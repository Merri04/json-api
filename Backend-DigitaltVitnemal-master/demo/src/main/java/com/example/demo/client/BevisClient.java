package com.example.demo.client;


import com.example.demo.DTO.TemplateResponseDTO;
import com.example.demo.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;



/**
 *

 @Author Elise Strand Bråtveit
 @Version 29.01.2025*/

@Component
public class BevisClient {

    private final WebClient webClient; // istedenfor RestTemplate

    @Autowired
    public BevisClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://prototype-lomino-issuer.azurewebsites.net").build();
    }

    public Mono<String> sendPostRequest() {
        String url = "/api/preauthorize";

        String templateId = "exampleType-61552628-BU";
        String idToken = JWTService.generateToken("10987654321");

        TemplateResponseDTO requestBody = new TemplateResponseDTO(templateId, idToken);

        return webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

    }
}