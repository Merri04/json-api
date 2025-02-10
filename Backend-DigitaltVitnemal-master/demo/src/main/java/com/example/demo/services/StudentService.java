package com.example.demo.services;

import com.example.demo.models.Student;
import com.example.demo.models.StudentPrincipal;
import com.example.demo.repositories.StudentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;


/**
 *
 * @Author Elise Strand Bråtveit og Merri Sium Berhe
 * @Version 22.01.2025
 */
@Service
public class StudentService implements UserDetailsService {

    @Autowired
    private Validator validator;
    private final StudentRepository studentRepo;

    public StudentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String fodselsnummer) throws UsernameNotFoundException {
        Student student = studentRepo.findByFodselsnummer(fodselsnummer);
        if (student == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        return new StudentPrincipal(student);
    }

@Transactional(readOnly = true)
    public Student getStudentByFodselsnummer(String fodselsnummer) {
        return studentRepo.findByFodselsnummer(fodselsnummer);
    }
    // Password validation method
    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }


    // This method is used to save a student to the database
    @Transactional
    public void saveStudent(Student student) {
        // Validate student data
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException(
                    "Validation failed: " + violations.stream()
                            .map(v -> v.getPropertyPath() + " " + v.getMessage())
                            .collect(Collectors.joining(", "))
            );
        }
        // Validate password
        if (!validatePassword(student.getPassword())) {
            throw new IllegalArgumentException(
                    "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
            );
        }
        // Hash the password before saving
        student.setPassword(new BCryptPasswordEncoder(12).encode(student.getPassword()));
        studentRepo.save(student);
    }


}
