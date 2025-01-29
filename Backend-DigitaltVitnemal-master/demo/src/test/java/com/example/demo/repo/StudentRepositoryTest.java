package com.example.demo.repo;

import com.example.demo.models.Student;
import com.example.demo.repositories.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class StudentRepositoryTest {

   /* @Autowired
    private StudentRepository studentRepository;

    @Test
    void testFindByStudentnummer() {
        // Arrange
        Student john = new Student(
                "S789012",
                "John Doe",
                "john@stud.ntnu.no",
                "1999-03-10",
                "123456",
                "Blå",
                "https://example.com/logo_ntnu.png"
        );
        studentRepository.save(john);

        // Act
        Student result = studentRepository.findByStudentnummer("S789012");

        // Assert
        assertNotNull(result);
        assertEquals("S789012", result.getStudentnummer());
    }*/
}

