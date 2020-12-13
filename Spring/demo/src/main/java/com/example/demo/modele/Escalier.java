package com.example.demo.modele;

import org.postgis.Geometry;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Escalier {
    @Id
    private Long id;
    private Integer etage_courant;
    private Integer etage_destination;

    public Escalier(){}

    public Escalier(Long id, Geometry geometry, Integer etage_courant, Integer etage_destination) {
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

    public void setEtage_courant(Integer etage_courant) {
        this.etage_courant = etage_courant;
    }

    public int getEtage_destination() {
        return etage_destination;
    }

    public void setEtage_destination(Integer etage_destination) {
        this.etage_destination = etage_destination;
    }

    @Override
    public boolean equals(Object obj) {
        Escalier escalier = (Escalier)obj;
        return escalier.getId() == id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
