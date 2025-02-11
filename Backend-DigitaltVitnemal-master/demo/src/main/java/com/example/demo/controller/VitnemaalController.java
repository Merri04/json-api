package com.example.demo.controller;


import com.example.demo.DTO.StudentVitnemaalDTO;
import com.example.demo.client.BevisClient;
import com.example.demo.services.StudentService;
import com.example.demo.services.VitnemaalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */

@RestController
@RequestMapping("/api/diplomas")
public class VitnemaalController {

    private final VitnemaalService vitnemaalService;
    private final StudentService studentService;
    private final BevisClient bevisClient;

    public VitnemaalController(VitnemaalService vitnemaalService,StudentService studentService,  BevisClient bevisClient ) {

        this.vitnemaalService = vitnemaalService;
        this.studentService = studentService;
        this.bevisClient =bevisClient;

    }


    @GetMapping(value = "/my-diplomas/{fodselsnummer}", produces = "application/json")
    public ResponseEntity<?> getAllDiplomaData(@PathVariable String fodselsnummer) {
        bevisClient.setFodelsesnummer(fodselsnummer);

        long studentId = studentService.getstudentIdByFodeselnummer(fodselsnummer);
        List<StudentVitnemaalDTO> studentDiplomas = vitnemaalService.getGroupedDiplomaData(studentId);
        return ResponseEntity.ok(studentDiplomas.stream().findFirst());
    }
}


