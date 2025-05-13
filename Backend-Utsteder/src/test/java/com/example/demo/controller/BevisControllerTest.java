package com.example.demo.controller;

import com.example.demo.client.BevisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BevisControllerTest {

    @Mock
    private BevisClient bevisClient;

    @InjectMocks
    private BevisController bevisController;

    @BeforeEach
    void setUp() {
        // Reset mocks if needed
    }

    @Test
    void getAllDiplomaData_whenSuccess_returnsUrl() {
        // Arrange
        String expectedUrl = "https://example.com/diploma";
        when(bevisClient.sendPostRequest()).thenReturn(Mono.just(expectedUrl));

        // Act & Assert
        StepVerifier.create(bevisController.getAllDiplomaData())
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.OK, response.getStatusCode());
                    assertEquals(expectedUrl, response.getBody().get("url"));
                    return true;
                })
                .verifyComplete();

        verify(bevisClient).sendPostRequest();
    }

    @Test
    void getAllDiplomaData_whenWebClientResponseException_returnsErrorUrl() {
        // Arrange
        String errorMessage = "Unauthorized access";
        WebClientResponseException ex = new WebClientResponseException(
                401, "Unauthorized", null,
                errorMessage.getBytes(StandardCharsets.UTF_8), null);

        when(bevisClient.sendPostRequest()).thenReturn(Mono.error(ex));

        // Act & Assert
        StepVerifier.create(bevisController.getAllDiplomaData())
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
                    assertEquals("Feil ved forespørsel: " + errorMessage, response.getBody().get("url"));
                    return true;
                })
                .verifyComplete();

        verify(bevisClient).sendPostRequest();
    }

    @Test
    void getAllDiplomaData_whenGenericException_returnsInternalServerError() {
        // Arrange
        RuntimeException ex = new RuntimeException("Tjenesten feilet");

        when(bevisClient.sendPostRequest()).thenReturn(Mono.error(ex));

        // Act & Assert
        StepVerifier.create(bevisController.getAllDiplomaData())
                .expectNextMatches(response -> {
                    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
                    assertEquals("Uventet feil: " + ex.getMessage(), response.getBody().get("url"));
                    return true;
                })
                .verifyComplete();

        verify(bevisClient).sendPostRequest();
    }
}
