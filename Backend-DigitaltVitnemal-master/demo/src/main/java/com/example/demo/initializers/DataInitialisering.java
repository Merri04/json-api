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


    private final StudentService studentService;

    @Autowired
    public DataInitialisering(StudentService studentService){
        this.studentService = studentService;
    }

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
                    "S1234567",
                    "John Doe",
                    "12345678901",
                    "Strong@password12",
                    true
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
            Karakter karakter3 = new Karakter(vitnemal1, "Machine Learning", "TDT4173", "A", 5, 2024);
            Karakter karakter4 = new Karakter(vitnemal1, "Visualisering", "DAFE234", "B", 10, 2025);
            Karakter karakter5 = new Karakter(vitnemal1, "Systemutvikling", "DAFE220", "A", 15, 2025);
            Karakter karakter6 = new Karakter(vitnemal1, "OS", "TDT4172", "B", 15, 2025);
            Karakter karakter7 = new Karakter(vitnemal1, "IOT", "TDT4453", "A", 15, 2025);
            Karakter karakter8 = new Karakter(vitnemal1, "Databaser", "ADTS2310", "A", 15, 2026);
            Karakter karakter9 = new Karakter(vitnemal1, "Programmering", "ADTS2332", "B", 15, 2026);
            Karakter karakter10 = new Karakter(vitnemal1, "Prototyping", "ADTS2360", "A", 15, 2026);
            Karakter karakter11 = new Karakter(vitnemal1, "Webutvikling", "ADTS1310", "B", 15, 2026);
            Karakter karakter12 = new Karakter(vitnemal1, "OS", "TDT4143", "A", 15, 2028);
            Karakter karakter13 = new Karakter(vitnemal2, "Datasikkerhet", "TDT2173", "A", 5, 2024);
            Karakter karakter14 = new Karakter(vitnemal2, "Algoritmer og Datastrukturer", "DAFE300", "B", 10, 2024);
            Karakter karakter15 = new Karakter(vitnemal2, "Machine Learning", "DATA3715", "B", 10, 2024);
            Karakter karakter16 = new Karakter(vitnemal2, "Visualisering", "TEK2345", "A", 10, 2024);
            Karakter karakter17 = new Karakter(vitnemal2, "OS", "TEK2333", "B", 10, 2025);
            Karakter karakter18 = new Karakter(vitnemal2, "IOT", "TDT4173", "A", 15, 2025);
            Karakter karakter19 = new Karakter(vitnemal2, "Databaser", "TDT2473", "B", 15, 2025);
            Karakter karakter20 = new Karakter(vitnemal2, "Programmering", "TEK2345", "B", 5, 2026);
            Karakter karakter21 = new Karakter(vitnemal2, "Prototyping", "ITPE2134", "A", 10, 2026);
            Karakter karakter22 = new Karakter(vitnemal2, "Webutvikling", "ITPE2124", "A", 10, 2026);


            karakterRepo.saveAll(List.of(karakter1, karakter2, karakter3, karakter4, karakter5, karakter6, karakter7, karakter8, karakter9, karakter10, karakter11, karakter12, karakter13, karakter14, karakter15, karakter16, karakter17, karakter18, karakter19, karakter20, karakter21, karakter22));
            logger.info("Karakterer er lagt til.");

        };
    }
}
