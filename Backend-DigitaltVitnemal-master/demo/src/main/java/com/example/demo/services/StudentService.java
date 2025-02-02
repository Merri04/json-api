package com.example.demo.services;

import com.example.demo.models.Student;
import com.example.demo.models.StudentPrincipal;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @Author Elise Strand Bråtveit
 * @Version 22.01.2025
 */
@Service
public class StudentService implements UserDetailsService {


    private final StudentRepository studentRepo;

    public StudentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String studentnr) throws UsernameNotFoundException {
        Student student = studentRepo.findByStudentnummer(studentnr);
        if (student == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        return new StudentPrincipal(student);
    }

@Transactional(readOnly = true)
    public Student getStudent(String studentnr) {
        return studentRepo.findByStudentnummer(studentnr);
    }


    // This method is used to save a student to the database
    @Transactional
    public void saveStudent(Student student) {
        // Hash the password before saving
        student.setPassword(new BCryptPasswordEncoder(12).encode(student.getPassword()));
        studentRepo.save(student);
    }


}
