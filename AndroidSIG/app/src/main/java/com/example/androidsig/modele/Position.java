package com.example.androidsig.modele;

import androidx.annotation.NonNull;

public class Position {

    private int id;
    private int idsalle;
    private int idescalier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsalle() {
        return idsalle;
    }

    public void setIdsalle(int idsalle) {
        this.idsalle = idsalle;
    }

    public int getIdescalier() {
        return idescalier;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " " + idsalle + " " + idescalier;
    }

    public void setIdescalier(int idescalier) {
        this.idescalier = idescalier;
    }
}
