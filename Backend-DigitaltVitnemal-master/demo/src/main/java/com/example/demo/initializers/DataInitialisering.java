package com.example.demo.initializers;


import com.example.demo.models.Karakter;
import com.example.demo.models.Student;
import com.example.demo.models.Utdanningssted;
import com.example.demo.models.Vitnemaal;
import com.example.demo.repositories.KarakterRepository;
import com.example.demo.repositories.UtdanningsstedRepository;
import com.example.demo.repositories.VitnemaalRepository;
import com.example.demo.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */
@Configuration
public class DataInitialisering {

    @Autowired
    private StudentService studentService; // this is for the password hashing in the saveStudent method

    private static final Logger logger = LoggerFactory.getLogger(DataInitialisering.class);

    @Bean
    @Transactional
    CommandLineRunner seedDatabase(UtdanningsstedRepository utdanningsstedRepo, VitnemaalRepository diplomaRepo, KarakterRepository karakterRepo) {
        return args -> {
            // Utdanningssteder
            Utdanningssted osloMet = new Utdanningssted(
                    "OsloMet"
            );

            Utdanningssted ntnu = new Utdanningssted(
                    "NTNU"
            );

            utdanningsstedRepo.saveAll(List.of(osloMet, ntnu));
            logger.info("Utdanningssteder er lagt til.");

            // student 1
            Student john = new Student(
                    "S1234567", "John Doe", "12345678901", "Strong@password12", true
            );

            // student 2
            Student merri = new Student(
                    "S1212",
                    "Merri Sium",
                    "10987654321",
                    "Strong@password22",
                    true
            );


            // hasher passwordet før lagring i databasen
            studentService.saveStudent(john);
            studentService.saveStudent(merri);

            logger.info("Student er lagt til.");

            //Diplomas
            Vitnemaal vitnemal1 = new Vitnemaal(merri, osloMet, "Bachelor in Computer Science", "2024-06-15");
            Vitnemaal vitnemal2 = new Vitnemaal(john, ntnu, "Master in Data Science", "2025-06-20");
            diplomaRepo.saveAll(List.of(vitnemal1, vitnemal2));
            logger.info("Diplomas added.");

            // Grades
            Karakter karakter1 = new Karakter(vitnemal1, "Datasikkerhet", "DATS200", "A", 20, 2024);
            Karakter karakter2 = new Karakter(vitnemal1, "Algoritmer og Datastrukturer", "DATS100", "B", 10, 2024);
            Karakter karakter3 = new Karakter(vitnemal2, "Machine Learning", "TDT4173", "A", 5, 2025);
            Karakter karakter4 = new Karakter(vitnemal2, "Visualisering", "TDT4173", "B", 10, 2027);

            karakterRepo.saveAll(List.of(karakter1, karakter2, karakter3, karakter4));
            logger.info("Karakterer er lagt til.");

        };
    }
}
