package com.example.demo.DTO;

import lombok.Data;

import java.util.List;

/**
 *
 * @Author Merri Sium Berhe
 * @Version 22.01.2025
 */
@Data
public class VitnemaalDTO {
    private String utdanningssted;
    private String grad;
    private List<KarakterDTO> karakterer;

    public VitnemaalDTO(String utdanningssted, String grad, List<KarakterDTO> karakterer) {
        this.utdanningssted = utdanningssted;
        this.grad = grad;
        this.karakterer = karakterer;
    }

    public String getUtdanningssted() {
        return utdanningssted;
    }

    public void setUtdanningssted(String utdanningssted) {
        this.utdanningssted = utdanningssted;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public List<KarakterDTO> getKarakterer() {
        return karakterer;
    }

    public void setKarakterer(List<KarakterDTO> karakterer) {
        this.karakterer = karakterer;
    }
}
