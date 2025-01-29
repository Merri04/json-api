package com.example.demo.service;

import com.example.demo.models.Student;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    /*@Test
    void testLoadUserByUsername() {
        // Arrange
        String studentnummer = "S789012";
        Student student = new Student(
                studentnummer,
                "John Doe",
                "john@stud.ntnu.no",
                "1999-03-10",
                "123456",
                "Blå",
                "https://example.com/logo_ntnu.png"
        );

        when(studentRepository.findByStudentnummer(studentnummer)).thenReturn(student);

        // Act
        UserDetails userDetails = studentService.loadUserByUsername(studentnummer);

        // Assert
        assertNotNull(userDetails);
        assertEquals("S789012", userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
    }

    @Test
    void testGetStudent() {
        // Arrange
        String studentnummer = "S789012";
        Student student = new Student(
                studentnummer,
                "John Doe",
                "john@stud.ntnu.no",
                "1999-03-10",
                "123456",
                "Blå",
                "https://example.com/logo_ntnu.png"
        );

        when(studentRepository.findByStudentnummer(studentnummer)).thenReturn(student);

        // Act
        Student result = studentService.getStudent(studentnummer);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getNavn());
        assertEquals("S789012", result.getStudentnummer());
    }*/
}

