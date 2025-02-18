package com.example.demo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */
@Data
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String studentnummer;
    private String navn;
    @NotBlank(message = "Fødselsnummer cannot be blank")
    @Pattern(regexp = "\\d{11}", message = "Fødselsnummer must be exactly 11 digits")
    private String username;
    private boolean fullfort;


    private String password; // Password for authentication

    // Parameterized constructor to initialize fields
    public Student(String studentnummer, String navn, String username, String password, boolean fullfort) {
        this.studentnummer = studentnummer;
        this.navn = navn;
        this.username = username;
        this.password = password;
        this.fullfort = fullfort;

    }

    // No-args constructor (required by JPA)
    public Student() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentnummer() {
        return studentnummer;
    }

    public void setStudentnummer(String studentnummer) {
        this.studentnummer = studentnummer;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String fodselsnummer) {
        this.username = fodselsnummer;
    }

    public String getPassword() {
        return password;
    }
    // setter for password
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isFullfort() {
        return fullfort;
    }
    public void setFullfort(boolean fullfort) {
        this.fullfort = fullfort;
    }

    public User orElseThrow(Object o) {
        return null;
    }
}
