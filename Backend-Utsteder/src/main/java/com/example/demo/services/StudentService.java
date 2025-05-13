package com.example.demo.services;

import com.example.demo.models.Student;
import com.example.demo.models.StudentPrincipal;
import com.example.demo.repositories.StudentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * @Author Elise Strand Bråtveit og Merri Sium Berhe
 * @Version 22.01.2025
 */
/*
 * Denne klassen håndterer autentisering og autorisering av brukere.
 * Den bruker UserDetailsService for å laste brukerdata fra databasen.
 * Den validerer også passord og lagrer studentdata i databasen.
 * Den bruker JWT for å generere tokens for autentisering.
 */
@Service
public class StudentService implements UserDetailsService {
    private final Validator validator;
    private final StudentRepository studentRepo;
    private final AuthenticationManager authenticationManager;
    private JWTService jwtService;

    @Autowired
    public StudentService(StudentRepository studentRepo, Validator validator, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.studentRepo = studentRepo;
        this.validator = validator;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String fodselsnummer) throws UsernameNotFoundException {
        Student student = studentRepo.findByUsername(fodselsnummer);
        if (student == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        return new StudentPrincipal(student);
    }

    // Denne var metoden brukes til å validere passordet
    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }

    // Denne metoden brukes til å lagre studentdata i databasen
    @Transactional
    public void saveStudent(Student student) {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(
                    "Validation failed: " + violations.stream()
                            .map(v -> v.getPropertyPath() + " " + v.getMessage())
                            .collect(Collectors.joining(", "))
            );
        }
        if (!validatePassword(student.getPassword())) {
            throw new IllegalArgumentException(
                    "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
            );
        }
        student.setPassword(new BCryptPasswordEncoder(12).encode(student.getPassword()));
        studentRepo.save(student);
    }

    public String verify(Student student) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(student.getUsername());
        }
        return "Fail";
    }
    public Optional<Long> getStudentIdByFodselsnummer(String fodselsnummer) {
        return Optional.ofNullable(studentRepo.findByUsername(fodselsnummer))
                .map(Student::getStudentId);
    }
}
