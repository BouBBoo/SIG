package com.example.androidsig.modele;

public class Voisin {
    private int idSalleCourante;
    private Salle voisinD;
    private Salle voisinG;
    private Salle voisinF;

    public Salle getVoisinD() {
        return voisinD;
    }

    public void setVoisinD(Salle voisinD) {
        this.voisinD = voisinD;
    }

    public Salle getVoisinG() {
        return voisinG;
    }

    public void setVoisinG(Salle voisinG) {
        this.voisinG = voisinG;
    }

    public Salle getVoisinF() {
        return voisinF;
    }

    public void setVoisinF(Salle voisinF) {
        this.voisinF = voisinF;
    }

    public Voisin() {
    }

    public int getIdSalleCourante() {
        return idSalleCourante;
    }

    public void setIdSalleCourante(int idSalleCourante) {
        this.idSalleCourante = idSalleCourante;
    }
}
