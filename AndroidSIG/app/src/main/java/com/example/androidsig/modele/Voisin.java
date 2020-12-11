package com.example.androidsig.modele;

public class Voisin {
    private int idSalleCourante;
    private Object voisinD;
    private Object voisinG;
    private Object voisinF;

    public Object getVoisinD() {
        if(voisinD == null){
            return null;
        }
        if(voisinD.getClass().equals(Salle.class))
            return (Salle)voisinD;
        if(voisinD.getClass().equals(Escalier.class)){
            return (Escalier)voisinD;
        }
        return null;
    }

    public void setVoisinD(Object voisinD) {
        this.voisinD = voisinD;
    }

    public Object getVoisinG() {
        if(voisinG == null){
            return null;
        }
        if(voisinG.getClass().equals(Salle.class))
            return (Salle)voisinG;
        if(voisinG.getClass().equals(Escalier.class)){
            return (Escalier)voisinG;
        }
        return null;
    }

    public void setVoisinG(Object voisinG) {
        this.voisinG = voisinG;
    }

    public Object getVoisinF() {
        if(voisinF == null){
            return null;
        }
        if(voisinF.getClass().equals(Salle.class))
            return (Salle)voisinF;
        if(voisinF.getClass().equals(Escalier.class)){
            return (Escalier)voisinF;
        }
        return null;
    }

    public void setVoisinF(Object voisinF) {
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
