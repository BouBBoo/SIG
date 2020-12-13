package com.example.androidsig.modele;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Escalier {
    private int id;
    private int etage_courant;
    private int etage_destination;

    public Escalier(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return id == ((Escalier)obj).id;
    }
}
