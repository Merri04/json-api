package com.example.demo.controller;



import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author Elise Strand Bråtveit & Merri
 * @Version 23.01.2025
 */

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public String home( Authentication authentication) {
        Student student = studentService.getStudent(authentication.getName());

        return student.getStudentnummer();
    }
}
