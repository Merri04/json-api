package com.example.demo.controller;


import com.example.demo.DTO.StudentVitnemaalDTO;
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

    public VitnemaalController(VitnemaalService vitnemaalService) {

        this.vitnemaalService = vitnemaalService;
    }


    @GetMapping(value = "/my-diplomas/{id}", produces = "application/json")
    public ResponseEntity<?> getAllDiplomaData(@PathVariable Long id) {

        List<StudentVitnemaalDTO> studentDiplomas = vitnemaalService.getGroupedDiplomaData(id);
        return ResponseEntity.ok(studentDiplomas);
    }
}


