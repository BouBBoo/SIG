package com.example.demo.modele;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EscalierJoint {
    @Id
    private Integer idEscalier1;
    private Integer idEscalier2;

    public EscalierJoint(){}

    public Integer getIdEscalier1() {
        return idEscalier1;
    }

    public void setIdEscalier1(Integer idEscalier1) {
        this.idEscalier1 = idEscalier1;
    }

    public Integer getIdEscalier2() {
        return idEscalier2;
    }

    public void setIdEscalier2(Integer idEscalier2) {
        this.idEscalier2 = idEscalier2;
    }
}
