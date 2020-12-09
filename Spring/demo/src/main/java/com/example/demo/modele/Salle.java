package com.example.demo.modele;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Salle implements Serializable {
    @Id
    private Long id;
    private String type_salle;
    private String nom;
    private Integer etage;

    public Salle(){}

    public Salle(long id, String type_salle, String nom, int etage) {
        this.id = id;
        this.type_salle = type_salle;
        this.nom = nom;
        this.etage = etage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType_salle() {
        return type_salle;
    }

    public void setType_salle(String type_salle) {
        this.type_salle = type_salle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }
}
