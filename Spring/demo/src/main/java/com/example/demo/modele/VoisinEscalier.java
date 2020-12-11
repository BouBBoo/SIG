package com.example.demo.modele;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VoisinEscalier {
    @Id
    private Long id;
    private Integer idescalier;
    private String direction;

    public VoisinEscalier(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEscalier() {
        return idescalier;
    }

    public void setEscalier(Integer escalier) {
        this.idescalier = escalier;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
