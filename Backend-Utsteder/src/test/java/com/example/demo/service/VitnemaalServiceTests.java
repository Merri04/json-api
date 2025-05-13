package com.example.demo.service;

import com.example.demo.DTO.KarakterDTO;
import com.example.demo.DTO.StudentVitnemaalDTO;
import com.example.demo.models.Karakter;
import com.example.demo.models.Student;
import com.example.demo.models.Utdanningssted;
import com.example.demo.models.Vitnemaal;
import com.example.demo.repositories.VitnemaalRepository;
import com.example.demo.services.VitnemaalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VitnemaalServiceTests {

    @Mock
    private VitnemaalRepository vitnemaalRepository;

    @InjectMocks
    private VitnemaalService vitnemaalService;

    private Vitnemaal vitnemaal;
    private Student student;
    private Utdanningssted utdanningssted;
    private Karakter karakter;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);
        student.setNavn("Merri Sium Berhe");
        student.setUsername("12345678910");
        student.setFullfort(true);

        utdanningssted = new Utdanningssted();
        utdanningssted.setUtdanningsnavn("OsloMet");

        karakter = new Karakter();
        karakter.setFag("Matematikk");
        karakter.setEmnekode("MAT101");
        karakter.setKarakter("A");
        karakter.setPoeng(10);
        karakter.setArstall(2025);

        vitnemaal = new Vitnemaal();
        vitnemaal.setStudent(student);
        vitnemaal.setUtdanningssted(utdanningssted);
        vitnemaal.setGrad("Bachelor");
        vitnemaal.setKarakterer(List.of(karakter));
        vitnemaal.setUtstedelsesdato(LocalDate.of(2025, 1, 15).toString());

    }

    // Test for å hente vitnemål med gyldig studentId
    @Test
    void testGetGroupedDiplomaData_ValidStudentId() {
        // Arrange
        when(vitnemaalRepository.findByStudentStudentId(1L)).thenReturn(List.of(vitnemaal));

        // Act
        List<StudentVitnemaalDTO> result = vitnemaalService.getGroupedDiplomaData(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        StudentVitnemaalDTO dto = result.get(0);
        assertEquals("Merri Sium Berhe", dto.getNavn());
        assertEquals("12345678910", dto.getFodselsnummer());
        assertEquals(true, dto.isFullfort());
        assertEquals("OsloMet", dto.getUtdanningsnavn());
        assertEquals("Bachelor", dto.getGrad());
        assertEquals(10, dto.getSum());
        assertEquals(LocalDate.of(2025, 1, 15), dto.getUtstedelsesdato());

        // Verifiser karakterdata
        assertEquals(1, dto.getKarakterer().size());
        KarakterDTO karakterDTO = dto.getKarakterer().get(0);
        assertEquals("Matematikk", karakterDTO.getFag());
        assertEquals("MAT101", karakterDTO.getEmnekode());
        assertEquals("A", karakterDTO.getKarakter());
        assertEquals(10, karakterDTO.getPoeng());
        assertEquals(2025, karakterDTO.getArstall());

        // Verifiser at metoden ble kalt én gang
        verify(vitnemaalRepository, times(1)).findByStudentStudentId(1L);
    }

    // Test for å hente vitnemål med ugyldig studentId (ingen resultater)
    @Test
    void testGetGroupedDiplomaData_InvalidStudentId() {
        // Arrange
        when(vitnemaalRepository.findByStudentStudentId(2L)).thenReturn(List.of());

        // Act
        List<StudentVitnemaalDTO> result = vitnemaalService.getGroupedDiplomaData(2L);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        // Verifiser at metoden ble kalt én gang
        verify(vitnemaalRepository, times(1)).findByStudentStudentId(2L);
    }

    // Test for å sikre at DTO-er er korrekt mappet
    @Test
    void testMappingToDTO() {
        // Arrange
        when(vitnemaalRepository.findByStudentStudentId(1L)).thenReturn(List.of(vitnemaal));

        // Act
        List<StudentVitnemaalDTO> result = vitnemaalService.getGroupedDiplomaData(1L);

        // Assert
        assertEquals(1, result.size());
        StudentVitnemaalDTO dto = result.get(0);
        assertEquals("Bachelor", dto.getGrad());
        assertEquals(10, dto.getSum());
        assertEquals("Merri Sium Berhe", dto.getNavn());
        assertEquals("OsloMet", dto.getUtdanningsnavn());
        assertEquals(1, dto.getKarakterer().size());
        KarakterDTO karakterDTO = dto.getKarakterer().get(0);
        assertEquals("Matematikk", karakterDTO.getFag());
        assertEquals("MAT101", karakterDTO.getEmnekode());
        assertEquals("A", karakterDTO.getKarakter());
    }
}

