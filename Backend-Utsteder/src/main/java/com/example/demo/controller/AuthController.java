package com.example.demo.controller;

import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
 * @Author Elise Strand Bråtveit & Merri Sium Berhe
 * @Version 17.02.2025
 */

/*
 * Denne controllerklassen håndterer forespørsel relatert til autentisering.
 * Den gir en API-endepunkt for innlogging og utlogging.
 */

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StudentService studentService;

    @Autowired
    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Student student) {
        try {
            String token = studentService.verify(student);

            if (token != null) {
                return ResponseEntity.ok().body(Map.of("token", token));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Something went wrong"));
        }
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok("Logged out successfully");
    }
}
