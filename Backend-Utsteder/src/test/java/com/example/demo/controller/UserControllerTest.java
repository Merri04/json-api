package com.example.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUser_whenAuthenticated_shouldReturnFodselsnummer() {
        // Arrange
        String mockFodselsnummer = "12345678901";
        Authentication authentication = new TestingAuthenticationToken(mockFodselsnummer, null);
        authentication.setAuthenticated(true);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // Act
        Map<String, String> result = userController.getCurrentUser();

        // Assert
        assertNotNull(result);
        assertEquals(mockFodselsnummer, result.get("fodselsnummer"));
    }

    @Test
    void getCurrentUser_whenNotAuthenticated_shouldThrowException() {
        // Arrange
        Authentication unauthenticated = new TestingAuthenticationToken("nope", null);
        unauthenticated.setAuthenticated(false);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(unauthenticated);
        SecurityContextHolder.setContext(context);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userController.getCurrentUser();
        });

        assertEquals("Ingen bruker er logget inn.", exception.getMessage());
    }

    @Test
    void getCurrentUser_whenAuthenticationIsNull_shouldThrowException() {
        // Arrange
        SecurityContextHolder.clearContext();

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userController.getCurrentUser();
        });

        assertEquals("Ingen bruker er logget inn.", exception.getMessage());
    }
}
