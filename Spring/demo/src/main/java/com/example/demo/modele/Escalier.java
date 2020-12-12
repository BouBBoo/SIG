package com.example.demo.modele;

import org.postgis.Geometry;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Escalier {
    @Id
    private Long id;
    private int etage_courant;
    private int etage_destination;

    public Escalier(){}

    public Escalier(Long id, Geometry geometry, int etage_courant, int etage_destination) {
        this.id = id;
        this.etage_courant = etage_courant;
        this.etage_destination = etage_destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEtage_courant() {
            return etage_courant;
    }

    public void setEtage_courant(int etage_courant) {
        this.etage_courant = etage_courant;
    }

    public int getEtage_destination() {
        return etage_destination;
    }

    public void setEtage_destination(int etage_destination) {
        this.etage_destination = etage_destination;
    }
}
