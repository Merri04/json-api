package com.example.demo.controller;

import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AuthController authController;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setUsername("12345678901");
        testStudent.setPassword("passord123");
    }

    @Test
    void login_whenCredentialsAreValid_returnsToken() {
        // Arrange
        when(studentService.verify(testStudent)).thenReturn("validToken");

        // Act
        ResponseEntity<?> response = authController.login(testStudent);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("token"));
        assertEquals("validToken", ((Map<?, ?>) response.getBody()).get("token"));
    }

    @Test
    void login_whenCredentialsAreInvalid_returnsUnauthorized() {
        // Arrange
        when(studentService.verify(testStudent)).thenReturn(null);

        // Act
        ResponseEntity<?> response = authController.login(testStudent);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error"));
        assertEquals("Invalid credentials", ((Map<?, ?>) response.getBody()).get("error"));
    }

    @Test
    void login_whenBadCredentialsExceptionThrown_returnsUnauthorized() {
        // Arrange
        when(studentService.verify(testStudent)).thenThrow(new BadCredentialsException("Invalid"));

        // Act
        ResponseEntity<?> response = authController.login(testStudent);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials", ((Map<?, ?>) response.getBody()).get("error"));
    }

    @Test
    void login_whenExceptionThrown_returnsServerError() {
        // Arrange
        when(studentService.verify(testStudent)).thenThrow(new RuntimeException("Something bad"));

        // Act
        ResponseEntity<?> response = authController.login(testStudent);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Something went wrong", ((Map<?, ?>) response.getBody()).get("error"));
    }

    @Test
    void logout_whenSessionExists_shouldInvalidateAndReturnOk() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(false)).thenReturn(session);

        // Act
        ResponseEntity<String> response = authController.logout(request);

        // Assert
        verify(session).invalidate();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out successfully", response.getBody());
    }

    @Test
    void logout_whenNoSession_shouldReturnOk() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(null);

        // Act
        ResponseEntity<String> response = authController.logout(request);

        // Assert
        verify(request).getSession(false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out successfully", response.getBody());
    }
}
