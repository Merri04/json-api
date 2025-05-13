package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */

@Entity
@Getter
@Setter
public class Karakter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long karakterId;

    @ManyToOne
    @JoinColumn(name = "vitnemal_id")
    @JsonIgnore
    private Vitnemaal vitnemaal;
    @JsonProperty("fag")
    private String fag;
    @JsonProperty("emnekode")
    private String emnekode;
    @JsonProperty("karakter")
    private String karakter;
    @JsonProperty("poeng")
    private int poeng;
    @JsonProperty("arstall")
    private int arstall;



    // Parameterized constructor
    public Karakter(Vitnemaal vitnemaal, String fag, String emnekode, String karakter, int poeng, int arstall) {
        this.vitnemaal = vitnemaal;
        this.fag = fag;
        this.karakter = karakter;
        this.emnekode = emnekode;
        this.poeng = poeng;
        this.arstall = arstall;

    }

    // Default constructor (required by JPA)
    public Karakter() {
    }


    public String getFag() {
        return fag;
    }

    public void setFag(String fag) {
        this.fag = fag;
    }

    public String getKarakter() {
        return karakter;
    }

    public Long getKarakterId() {
        return karakterId;
    }

    public void setKarakterId(Long karakterId) {
        this.karakterId = karakterId;
    }

    public Vitnemaal getVitnemaal() {
        return vitnemaal;
    }

    public void setVitnemaal(Vitnemaal vitnemaal) {
        this.vitnemaal = vitnemaal;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }

    public void setKarakter(String karakter) {
        this.karakter = karakter;
    }

    public int getPoeng() {
        return poeng;
    }

    public void setPoeng(int poeng) {
        this.poeng = poeng;
    }

    public int getArstall() {
        return arstall;
    }

    public void setArstall(int arstall) {
        this.arstall = arstall;
    }

}
