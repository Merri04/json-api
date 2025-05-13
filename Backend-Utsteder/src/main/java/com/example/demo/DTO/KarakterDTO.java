package com.example.demo.DTO;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */
public class KarakterDTO {
    private String fag;
    private String emnekode;
    private String karakter;
    private int poeng;
    private int arstall;

    public KarakterDTO(String fag, String emnekode, String karakter, int poeng, int arstall) {
        this.fag = fag;
        this.emnekode = emnekode;
        this.karakter = karakter;
        this.poeng = poeng;
        this.arstall = arstall;
    }

    public String getFag() {
        return fag;
    }

    public void setFag(String fag) {
        this.fag = fag;
    }

    public String getEmnekode() {
        return emnekode;
    }

    public void setEmnekode(String emnekode) {
        this.emnekode = emnekode;
    }

    public String getKarakter() {
        return karakter;
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
