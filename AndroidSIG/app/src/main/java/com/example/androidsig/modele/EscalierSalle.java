package com.example.androidsig.modele;

import androidx.annotation.NonNull;

public class EscalierSalle {
    private int id;
    private int idvoisind;
    private int idvoising;
    private int idvoisinf;

    @NonNull
    @Override
    public String toString() {
        return idvoisind + ", " + idvoisinf + ", " + idvoising;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdvoisind() {
        return idvoisind;
    }

    public void setIdvoisind(int idvoisind) {
        this.idvoisind = idvoisind;
    }

    public int getIdvoising() {
        return idvoising;
    }

    public void setIdvoising(int idvoising) {
        this.idvoising = idvoising;
    }

    public int getIdvoisinf() {
        return idvoisinf;
    }

    public void setIdvoisinf(int idvoisinf) {
        this.idvoisinf = idvoisinf;
    }
}
