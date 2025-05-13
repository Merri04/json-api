package com.example.demo.repositories;


import com.example.demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
 *
 * @Author Merri Sium Berhe & Elise Strand Bråtveit
 * @Version 22.01.2025
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String fodselsnummer);
}

