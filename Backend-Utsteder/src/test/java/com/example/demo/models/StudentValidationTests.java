package com.example.demo.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 10.02.2025
 */

class StudentValidationTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidStudent() {
        Student student = new Student();
        student.setUsername("12345678901");      // Valid fødselsnummer
        student.setPassword("Strong@123");       // Valid password

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertTrue(violations.isEmpty(), "Expected no validation errors for a valid student");
    }

    @Test
    void testInvalidFodselsnummer() {
        Student student = new Student();
        student.setUsername("12345");          // Invalid fødselsnummer (too short)
        student.setPassword("Strong@123");     // Valid password

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Expected validation errors for an invalid fødselsnummer");

        // Check specific error messages
        violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("fodselsnummer"))
                .forEach(v -> {
                    System.out.println("Error: " + v.getMessage());
                });
    }



    @Test
    void testBlankFodselsnummerAndPassword() {
        Student student = new Student();
        student.setUsername("");                // Blank fødselsnummer
        student.setPassword("");                // Blank password

        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        assertFalse(violations.isEmpty(), "Expected validation errors for blank fields");

        // Check specific error messages
        violations.forEach(v -> {
            System.out.println("Error in field '" + v.getPropertyPath() + "': " + v.getMessage());
        });
    }
}
