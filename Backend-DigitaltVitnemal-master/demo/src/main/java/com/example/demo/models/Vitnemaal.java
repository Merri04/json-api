package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */

@Entity
public class Vitnemaal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vitnemalId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "utdanningssted_id")
    private Utdanningssted utdanningssted;

    @Column(name = "grad", nullable = false, length = 50)
    private String grad;

    private String utstedelsesdato;

    @OneToMany(mappedBy = "vitnemaal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Denne styrer serialiseringen av relasjonen
    private List<Karakter> karakterer;

    // No-args constructor
    public Vitnemaal() {
    }

    // Corrected constructor
    public Vitnemaal(Student student, Utdanningssted utdanningssted, String grad, String utstedelsesdato) {
        this.student = student;
        this.utdanningssted = utdanningssted;
        this.grad = grad;
        this.utstedelsesdato = utstedelsesdato;
    }
    public int calculateTotalPoints() {
        return karakterer.stream()
                .mapToInt(Karakter::getPoeng)
                .sum();
    }

    public Utdanningssted getUtdanningssted() {
        return utdanningssted;
    }

    public void setUtdanningssted(Utdanningssted utdanningssted) {
        this.utdanningssted = utdanningssted;
    }

    // Getters and Setters
    public Long getVitnemalId() {
        return vitnemalId;
    }

    public void setVitnemalId(Long vitnemalId) {
        this.vitnemalId = vitnemalId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getUtstedelsesdato() {
        return utstedelsesdato;
    }

    public void setUtstedelsesdato(String utstedelsesdato) {
        this.utstedelsesdato = utstedelsesdato;
    }

    public List<Karakter> getKarakterer() {
        return karakterer;
    }

    public void setKarakterer(List<Karakter> karakterer) {
        this.karakterer = karakterer;
    }
}
