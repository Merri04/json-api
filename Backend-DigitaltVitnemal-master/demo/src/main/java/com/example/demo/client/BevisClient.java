package com.example.demo.client;


import com.example.demo.DTO.TemplateResponseDTO;
import com.example.demo.services.JWTService;
import com.example.demo.services.StudentService;
import com.example.demo.util.ApiKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


/**
 *
 * @Author Elise Strand Bråtveit
 @Version 29.01.2025
 */

//TODO: Sjekk denne klassen JWT - JWT token er hardkodet inn for å testet

@Component
public class BevisClient {

    private final WebClient webClient;
    private String fodselsnummer;
    private final JWTService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(BevisClient.class);

    @Autowired
    public BevisClient(WebClient.Builder webClientBuilder, JWTService jwtService ) {
        this.webClient = webClientBuilder.baseUrl("https://prototype-lomino-issuer.azurewebsites.net").build();
        this.jwtService = jwtService;
    }

    public void setFodelsesnummer(String fodselsnummer){
        this.fodselsnummer = fodselsnummer;
    }

    public Mono<String> sendPostRequest() {
        String url = "/api/preauthorize";

        String templateId = "vitnemal-833671fd-BU";
        //String idToken = jwtService.generateToken("10987654321");
        String idToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDk4NzY1NDMyMSIsImlhdCI6MTczOTU0Nzc1NSwiZXhwIjoxNzQwMTUyNTU1fQ.2BMCMAfkRdPJDGWvyoN6pLW8xGir7ZAaSRgB33ruyvo";
        logger.info("jwt-token  " + idToken);
        //logger.info("x-api-key " + ApiKeyUtil.generateApiKey());


        TemplateResponseDTO requestBody = new TemplateResponseDTO(templateId, idToken);

        return webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

    }
}