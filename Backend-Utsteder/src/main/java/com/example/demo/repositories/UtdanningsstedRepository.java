package com.example.demo.repositories;


import com.example.demo.models.Utdanningssted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */
@Repository
public interface UtdanningsstedRepository extends JpaRepository<Utdanningssted, Long> {

}
