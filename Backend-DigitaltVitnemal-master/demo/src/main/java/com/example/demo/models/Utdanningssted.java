package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */

@Entity
@Getter
@Setter
public class Utdanningssted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long utdanningsstedId;

    private String utdanningsnavn;

    public Utdanningssted(String utdaningsnavn) {
        this.utdanningsnavn = utdaningsnavn;
    }

    public Utdanningssted() {

    }

    public Long getUtdanningsstedId() {
        return utdanningsstedId;
    }

    public void setUtdanningsstedId(Long utdanningsstedId) {
        this.utdanningsstedId = utdanningsstedId;
    }

    public String getUtdanningsnavn() {
        return utdanningsnavn;
    }

    public void setUtdanningsnavn(String utdanningsnavn) {
        this.utdanningsnavn = utdanningsnavn;
    }
}
