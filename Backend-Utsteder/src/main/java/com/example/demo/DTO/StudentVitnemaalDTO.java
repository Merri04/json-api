package com.example.demo.DTO;

import java.time.LocalDate;
import java.util.List;

/*
 *
 * @Author Merri Sium Berhe & Elise Strand Bråtveit
 * @Version 22.01.2025
 */

public class StudentVitnemaalDTO {
    private String navn;
    private String fodselsnummer;
    private boolean fullfort;
    private String utdanningsnavn;
    private String grad;
    private int sum;
    private LocalDate utstedelsesdato;
    private List<KarakterDTO> karakterer;

    public StudentVitnemaalDTO(String navn, String fodselsnummer, boolean fullfort, String utdanningsnavn, String grad, int sum, String utstedelsesdato, List<KarakterDTO> karakterer) {
        this.navn = navn;
        this.fodselsnummer = fodselsnummer;
        this.fullfort = fullfort;
        this.utdanningsnavn = utdanningsnavn;
        this.grad = grad;
        this.sum = sum;
        this.utstedelsesdato = utstedelsesdato != null ? LocalDate.parse(utstedelsesdato) : null;
        this.karakterer = karakterer;

    }

    public LocalDate getUtstedelsesdato() {
        return utstedelsesdato;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getFodselsnummer() {
        return fodselsnummer;
    }

    public void setFodselsnummer(String fodselsnummer) {
        this.fodselsnummer = fodselsnummer;
    }

    public boolean isFullfort() {
        return fullfort;
    }

    public void setFullfort(boolean fullfort) {
        this.fullfort = fullfort;
    }

    public String getUtdanningsnavn() {
        return utdanningsnavn;
    }

    public void setUtdanningsnavn(String utdanningsnavn) {
        this.utdanningsnavn = utdanningsnavn;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<KarakterDTO> getKarakterer() {
        return karakterer;
    }

    public void setKarakterer(List<KarakterDTO> karakterer) {
        this.karakterer = karakterer;
    }
}
