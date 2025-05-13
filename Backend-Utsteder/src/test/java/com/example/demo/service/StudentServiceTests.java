package com.example.demo.service;

import com.example.demo.models.Student;
import com.example.demo.models.StudentPrincipal;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentService;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTests {

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private Validator validator;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);
        student.setUsername("12345678910");
        student.setPassword("Secure@123");
    }

    // Test for å hente student med gyldig brukernavn
    @Test
    void testLoadUserByUsername_ValidUsername() {
        // Arrange
        when(studentRepo.findByUsername("12345678910")).thenReturn(student);

        // Act
        StudentPrincipal result = (StudentPrincipal) studentService.loadUserByUsername("12345678910");

        // Assert
        assertNotNull(result);
        assertEquals("12345678910", result.getUsername());
    }

    // Test for å hente student med ugyldig brukernavn
    @Test
    void testLoadUserByUsername_InvalidUsername() {
        // Arrange
        when(studentRepo.findByUsername("12345678910")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> studentService.loadUserByUsername("12345678910"));
    }

    // Test for lagring av gyldig student
    @Test
    void testSaveStudent_ValidStudent() {
        // Arrange
        when(validator.validate(student)).thenReturn(Set.of());
        when(studentRepo.save(any(Student.class))).thenReturn(student);

        // Act
        studentService.saveStudent(student);

        // Assert
        verify(studentRepo, times(1)).save(any(Student.class));
        assertTrue(new BCryptPasswordEncoder(12).matches("Secure@123", student.getPassword()));
    }

    // Test for lagring av student med ugyldig passord
    @Test
    void testSaveStudent_InvalidPassword() {
        // Arrange
        student.setPassword("invalid");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> studentService.saveStudent(student));
        assertEquals("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character", exception.getMessage());

        verify(studentRepo, never()).save(any());
    }

    // Test for autentisering av ugyldig student
    @Test
    void testVerify_InvalidStudent() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Act
        String result = studentService.verify(student);

        // Assert
        assertEquals("Fail", result);
    }

    //  Test for å hente studentId med gyldig fodselsnummer
    @Test
    void testGetStudentIdByFodselsnummer_Valid() {
        // Arrange
        when(studentRepo.findByUsername("12345678910")).thenReturn(student);

        // Act
        Optional<Long> studentId = studentService.getStudentIdByFodselsnummer("12345678910");

        // Assert
        assertTrue(studentId.isPresent());
        assertEquals(1L, studentId.get());
    }

    // Test for å hente studentId med ugyldig fodselsnummer
    @Test
    void testGetStudentIdByFodselsnummer_Invalid() {
        // Arrange
        when(studentRepo.findByUsername("12345678910")).thenReturn(null);

        // Act
        Optional<Long> studentId = studentService.getStudentIdByFodselsnummer("12345678910");

        // Assert
        assertFalse(studentId.isPresent());
    }
}
