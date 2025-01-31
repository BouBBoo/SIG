package com.example.androidsig.modele;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Salle{
    private long id;
    private String type_salle;
    private String nom;
    private int etage;

    public Salle(){}

    public Salle(long id, String type_salle, String nom, int etage) {
        this.id = id;
        this.type_salle = type_salle;
        this.nom = nom;
        this.etage = etage;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getId() == ((Salle)obj).getId();
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
