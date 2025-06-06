package com.example.demo.client;

import com.example.demo.DTO.TemplateResponseDTO;
import com.example.demo.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/*
 *
 * @Author Elise Strand Bråtveit
 @Version 29.01.2025
 */

/*
 *
 * Denne klassen er en klient for å sende forespørsel til Bevis API.
 * Den bruker WebClient fra Spring WebFlux for å gjøre asynkrone HTTP-forespørsel.
 *
 */
@Component
public class BevisClient {
    private final WebClient webClient;
    private final JWTService jwtService;
    private String fodselsnummer;

    @Autowired
    public BevisClient(WebClient.Builder webClientBuilder, JWTService jwtService) {
        this.webClient = webClientBuilder.baseUrl("https://prototype-lomino-issuer.azurewebsites.net").build();
        this.jwtService = jwtService;
    }
    public void setFodelsesnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
    }

    public Mono<String> sendPostRequest() {
        String url = "/api/preauthorize";
        String templateId = "vitnemal-833671fd-BU";
        String idToken = jwtService.generateToken(fodselsnummer);
        String credentialFormat = "jwt_vc";

        TemplateResponseDTO requestBody = new TemplateResponseDTO(templateId, idToken, credentialFormat);
        return webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + jwtService.generateToken(fodselsnummer))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println(" iGrant error response: " + errorBody);
                                return Mono.error(new RuntimeException("iGrant-feil: " + errorBody));
                            });
                })
                .bodyToMono(String.class);


    }
}



