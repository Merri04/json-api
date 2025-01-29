package com.example.demo.controller;


import com.example.demo.DTO.StudentVitnemaalDTO;
import com.example.demo.models.StudentPrincipal;
import com.example.demo.services.VitnemaalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/my-diplomas", produces = "application/json")
    public ResponseEntity<?> getAllDiplomaData(@AuthenticationPrincipal StudentPrincipal studentPrincipal) {
        Long studentId = studentPrincipal.getStudentId();
        List<StudentVitnemaalDTO> studentDiplomas = vitnemaalService.getGroupedDiplomaData(studentId);
        return ResponseEntity.ok(studentDiplomas);
    }
    // post mapping for sending diploma to bevisstudio kommer her etterhvert som vi får implementert det
}
