package com.example.demo.modele;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Salle implements Serializable {
    @Id
    private long id;
    private String fonction;
    private int etage;

    public Salle(){}

    public Salle(long id, String fonction, int etage) {
        this.id = id;
        this.fonction = fonction;
        this.etage = etage;
    }

    @Override
    public String toString() {
        return "id: " + id + ", fonction: " + fonction + ", etage: " + etage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }
}
