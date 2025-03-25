package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }


    // Test for manglende autentisering
    @Test
    void testGetCurrentUser_UnauthenticatedUser() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userController.getCurrentUser();
        });

        assertEquals("Ingen bruker er logget inn.", exception.getMessage());

        // ✅ Verifiser at autentisering ble hentet
        verify(securityContext).getAuthentication();
    }

    // ✅ Test for manglende innlogging (ikke autentisert)
    @Test
    void testGetCurrentUser_NotAuthenticated() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userController.getCurrentUser();
        });

        assertEquals("Ingen bruker er logget inn.", exception.getMessage());

        // ✅ Verifiser at autentisering ble hentet
        verify(securityContext).getAuthentication();
        verify(authentication).isAuthenticated();
    }

    // ✅ Test for logout med aktiv session
    @Test
    void testLogout_WithActiveSession() {
        // Arrange
        when(request.getSession(false)).thenReturn(session);

        // Act
        ResponseEntity<String> response = userController.logout(request);

        // Assert
        assertNotNull(response);
        assertEquals("Logged out successfully", response.getBody());
        assertEquals(200, response.getStatusCode().value());

        // ✅ Verifiser at session ble invalidert
        verify(session).invalidate();
    }

    // ✅ Test for logout uten aktiv session
    @Test
    void testLogout_NoActiveSession() {
        // Arrange
        when(request.getSession(false)).thenReturn(null);

        // Act
        ResponseEntity<String> response = userController.logout(request);

        // Assert
        assertNotNull(response);
        assertEquals("Logged out successfully", response.getBody());
        assertEquals(200, response.getStatusCode().value());

        // ✅ Verifiser at ingen session ble invalidert
        verifyNoInteractions(session);
    }
}
