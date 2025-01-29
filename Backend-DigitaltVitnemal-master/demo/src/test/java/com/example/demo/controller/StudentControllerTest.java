package com.example.demo.controller;

import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Mock
    private Authentication authentication;

    /*@Test
    void testHome() {
        // Arrange
        String studentNumber = "S789012";
        Student john = new Student(
                studentNumber,
                "John Doe",
                "john@stud.ntnu.no",
                "1999-03-10",
                "123456",
                "Blå",
                "https://example.com/logo_ntnu.png"
        );

        when(authentication.getName()).thenReturn(studentNumber);
        when(studentService.getStudent(studentNumber)).thenReturn(john);

        // Act
        String result = studentController.home(authentication);

        // Assert
        assertNotNull(result);
        assertEquals(studentNumber, result);
        verify(authentication, times(1)).getName();
        verify(studentService, times(1)).getStudent(studentNumber);
    }*/
}
