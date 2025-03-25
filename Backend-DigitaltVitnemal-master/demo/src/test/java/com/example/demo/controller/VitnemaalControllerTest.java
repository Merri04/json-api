package com.example.demo.controller;

import com.example.demo.DTO.StudentVitnemaalDTO;
import com.example.demo.client.BevisClient;
import com.example.demo.services.StudentService;
import com.example.demo.services.VitnemaalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VitnemaalControllerTest {

    @Mock
    private VitnemaalService vitnemaalService;

    @Mock
    private StudentService studentService;

    @Mock
    private BevisClient bevisClient;

    @InjectMocks
    private VitnemaalController vitnemaalController;

    private StudentVitnemaalDTO mockDiploma;

    @BeforeEach
    void setUp() {
        mockDiploma = new StudentVitnemaalDTO(
                "John Doe",
                "12345678901",
                true,
                "OsloMet",
                "Bachelor",
                180,
                "2025-06-20",
                List.of()
        );
    }

    // Test for suksessrespons
    @Test
    void testGetAllDiplomaData_Success() {
        // Arrange
        when(studentService.getStudentIdByFodselsnummer(anyString()))
                .thenReturn(Optional.of(1L));
        when(vitnemaalService.getGroupedDiplomaData(1L))
                .thenReturn(List.of(mockDiploma));

        // Act
        ResponseEntity<?> response = vitnemaalController.getAllDiplomaData("12345678901");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Optional);
        Optional<?> body = (Optional<?>) response.getBody();
        assertTrue(body.isPresent());
        assertEquals(mockDiploma, body.get());

        // Verifiser at BevisClient og services ble kalt riktig
        verify(bevisClient).setFodelsesnummer("12345678901");
        verify(studentService).getStudentIdByFodselsnummer("12345678901");
        verify(vitnemaalService).getGroupedDiplomaData(1L);
    }

    // ✅ Test for manglende student
    @Test
    void testGetAllDiplomaData_StudentNotFound() {
        // Arrange
        when(studentService.getStudentIdByFodselsnummer(anyString()))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = vitnemaalController.getAllDiplomaData("12345678901");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Ingen student funnet med fødselsnummer: 12345678901", response.getBody());

        // Verifiser at BevisClient og StudentService ble kalt riktig
        verify(bevisClient).setFodelsesnummer("12345678901");
        verify(studentService).getStudentIdByFodselsnummer("12345678901");
        verifyNoInteractions(vitnemaalService);
    }

    // ✅ Test for ingen vitnemål funnet
    @Test
    void testGetAllDiplomaData_NoDiplomasFound() {
        // Arrange
        when(studentService.getStudentIdByFodselsnummer(anyString()))
                .thenReturn(Optional.of(1L));
        when(vitnemaalService.getGroupedDiplomaData(1L))
                .thenReturn(List.of()); // Ingen vitnemål funnet

        // Act
        ResponseEntity<?> response = vitnemaalController.getAllDiplomaData("12345678901");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Ingen vitnemål funnet for denne studenten.", response.getBody());

        // Verifiser at BevisClient og services ble kalt riktig
        verify(bevisClient).setFodelsesnummer("12345678901");
        verify(studentService).getStudentIdByFodselsnummer("12345678901");
        verify(vitnemaalService).getGroupedDiplomaData(1L);
    }

    // ✅ Test for feil under databehandling
    @Test
    void testGetAllDiplomaData_ExceptionThrown() {
        // Arrange
        when(studentService.getStudentIdByFodselsnummer(anyString()))
                .thenThrow(new RuntimeException("Feil i database"));

        // Act & Assert
        try {
            vitnemaalController.getAllDiplomaData("12345678901");
        } catch (Exception e) {
            assertEquals("Feil i database", e.getMessage());
        }

        // Verifiser at BevisClient ble kalt, men ikke service
        verify(bevisClient).setFodelsesnummer("12345678901");
        verify(studentService).getStudentIdByFodselsnummer("12345678901");
        verifyNoInteractions(vitnemaalService);
    }
}
