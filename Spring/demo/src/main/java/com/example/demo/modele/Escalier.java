package com.example.demo.modele;

import org.postgis.Geometry;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Escalier {
    @Id
    private int id;
    private Geometry geometry;
    private int etage_courant;
    private int etage_destination;

    public Escalier(){}

    public Escalier(int id, Geometry geometry, int etage_courant, int etage_destination) {
        this.id = id;
        this.geometry = geometry;
        this.etage_courant = etage_courant;
        this.etage_destination = etage_destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
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
