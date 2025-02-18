package com.example.demo.controller;


import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *

 @Author Elise Strand Bråtveit
 @Version 17.02.2025

 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StudentService studentService;

    @Autowired
    public AuthController(StudentService studentService){
        this.studentService = studentService;
    }
    @PostMapping("/login")
    public String login(@RequestBody Student student){
        return studentService.verify(student);
    }



}
