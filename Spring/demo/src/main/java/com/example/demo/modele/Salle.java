package com.example.demo.modele;

import org.postgis.Geometry;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Salle implements Serializable {
    @Id
    private int id;
    private String fonction;
    private int etage;

    public Salle(){}

    public Salle(int id, String fonction, int etage) {
        this.id = id;
        this.fonction = fonction;
        this.etage = etage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
