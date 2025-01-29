package com.example.demo.repositories;


import com.example.demo.models.Vitnemaal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */
@Repository
public interface VitnemaalRepository extends JpaRepository<Vitnemaal, Long> {

    // Fetch all diplomas from the database
    // make a try and catch block to handle exceptions and fetch all diplomas from the database
    @EntityGraph(attributePaths = {"student", "utdanningssted", "karakterer"})
    List<Vitnemaal> findByStudentStudentId(Long studentId);
}
