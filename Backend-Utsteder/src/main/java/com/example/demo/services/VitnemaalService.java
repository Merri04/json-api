package com.example.demo.services;

import com.example.demo.DTO.KarakterDTO;
import com.example.demo.DTO.StudentVitnemaalDTO;
import com.example.demo.models.Vitnemaal;
import com.example.demo.repositories.VitnemaalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/*
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */

/*
 * Denne servicen er ansvarlig for å håndtere logikken knyttet til vitnemål.
 * Den henter vitnemål fra databasen og mapper dem til DTO-er for å returnere til klienten.
 */
@Service
public class VitnemaalService {

    private final VitnemaalRepository vitnemaalRepository;

    public VitnemaalService(VitnemaalRepository vitnemaalRepository) {
        this.vitnemaalRepository = vitnemaalRepository;
    }
    public List<StudentVitnemaalDTO> getGroupedDiplomaData(Long studentId) {
        List<Vitnemaal> vitnemaals = vitnemaalRepository.findByStudentStudentId(studentId);
        return vitnemaals.stream().map(vitnemal -> {
        // Map alle vitnemål til StudentVitnemaalDTO
        return new StudentVitnemaalDTO(
                        vitnemal.getStudent().getNavn(),
                        vitnemal.getStudent().getUsername(),
                        vitnemal.getStudent().isFullfort(),
                        vitnemal.getUtdanningssted().getUtdanningsnavn(),
                        vitnemal.getGrad(),
                        vitnemal.calculateTotalPoints(),
                        vitnemal.getUtstedelsesdato(),
                        vitnemal.getKarakterer().stream()
                                .map(karakter -> new KarakterDTO(
                                        karakter.getFag(),
                                        karakter.getEmnekode(),
                                        karakter.getKarakter(),
                                        karakter.getPoeng(),
                                        karakter.getArstall()))
                                .collect(Collectors.toList())
                );
        }).collect(Collectors.toList());
    }
}
