package com.example.demo.initializers;

import com.example.demo.models.Karakter;
import com.example.demo.models.Student;
import com.example.demo.models.Utdanningssted;
import com.example.demo.models.Vitnemaal;
import com.example.demo.repositories.KarakterRepository;
import com.example.demo.repositories.UtdanningsstedRepository;
import com.example.demo.repositories.VitnemaalRepository;
import com.example.demo.services.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitialiseringTests {

    @Mock
    private UtdanningsstedRepository utdanningsstedRepo;

    @Mock
    private VitnemaalRepository diplomaRepo;


    @Mock
    private KarakterRepository karakterRepo;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private DataInitialisering dataInitialisering;

    private Student john;
    private Student merri;
    private Utdanningssted osloMet;
    private Utdanningssted ntnu;
    private Vitnemaal vitnemal1;
    private Vitnemaal vitnemal2;
    private Karakter karakter1;
    private Karakter karakter2;

    @BeforeEach
    void setUp() {
        // Opprett utdanningssteder
        osloMet = new Utdanningssted("OsloMet");
        ntnu = new Utdanningssted("NTNU");

        // Opprett studenter
        john = new Student("S1234567", "John Doe", "12345678901", "Strong@password12", true);
        merri = new Student("S1212", "Merri Sium", "10987654321", "Strong@password22", true);

        // Opprett vitnemål
        vitnemal1 = new Vitnemaal(merri, osloMet, "Bachelor in Computer Science", "2024-06-15");
        vitnemal2 = new Vitnemaal(john, ntnu, "Master in Data Science", "2025-06-20");

        // Opprett karakterer
        karakter1 = new Karakter(vitnemal1, "Datasikkerhet", "DATS200", "A", 20, 2024);
        karakter2 = new Karakter(vitnemal2, "Algoritmer og Datastrukturer", "DAFE300", "B", 10, 2024);
    }

    //  Test for initialisering av utdanningssteder
    @Test
    void testInitializeEducationInstitutions() throws Exception {
        // Act
        dataInitialisering.seedDatabase(utdanningsstedRepo, diplomaRepo, karakterRepo).run();

        // Assert
        verify(utdanningsstedRepo, times(1)).saveAll(any());
    }

    // Test for initialisering av studenter
    @Test
    void testInitializeStudents() throws Exception {
        // Act
        dataInitialisering.seedDatabase(utdanningsstedRepo, diplomaRepo, karakterRepo).run();

        // Assert
        verify(studentService, times(2)).saveStudent(any());
    }

    //  Test for initialisering av vitnemål
    @Test
    void testInitializeDiplomas() throws Exception {
        // Act
        dataInitialisering.seedDatabase(utdanningsstedRepo, diplomaRepo, karakterRepo).run();

        // Assert
        verify(diplomaRepo, times(1)).saveAll(any());
    }

    //  Test for initialisering av karakterer
    @Test
    void testInitializeGrades() throws Exception {
        // Act
        dataInitialisering.seedDatabase(utdanningsstedRepo, diplomaRepo, karakterRepo).run();

        // Assert
        verify(karakterRepo, times(1)).saveAll(any());
    }


    // Test for riktig mapping av vitnemål
    @Test
    void testDiplomaMapping() throws Exception {
        // Act
        dataInitialisering.seedDatabase(utdanningsstedRepo, diplomaRepo, karakterRepo).run();

        // Fix: Bruk StreamSupport for Iterable
        verify(diplomaRepo).saveAll(argThat(diplomas ->
                StreamSupport.stream(diplomas.spliterator(), false)
                        .anyMatch(v ->
                                v.getGrad().equals("Bachelor in Computer Science") &&
                                        v.getUtstedelsesdato().equals("2024-06-15") &&
                                        v.getUtdanningssted().getUtdanningsnavn().equals("OsloMet")
                        )
        ));
    }

    //  Test for riktig mapping av karakterer
    @Test
    void testGradeMapping() throws Exception {
        // Act
        dataInitialisering.seedDatabase(utdanningsstedRepo, diplomaRepo, karakterRepo).run();

        // Fix: Bruk StreamSupport for Iterable
        verify(karakterRepo).saveAll(argThat(grades ->
                StreamSupport.stream(grades.spliterator(), false)
                        .anyMatch(k ->
                                k.getFag().equals("Datasikkerhet") &&
                                        k.getEmnekode().equals("DATS200") &&
                                        k.getKarakter().equals("A") &&
                                        k.getPoeng() == 20 &&
                                        k.getArstall() == 2024
                        )
        ));
    }
}
